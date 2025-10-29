package hospital.presentacion.usuario;

import hospital.logic.Protocol;
import hospital.logic.Usuario;
import hospital.presentacion.ThreadListener;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketListener {
    ThreadListener listener;
    String sid; // Session id
    Socket as;  // Asynch socket
    ObjectOutputStream aos;
    ObjectInputStream ais;

    public SocketListener(ThreadListener listener, String SID) throws Exception {
        this.listener = listener;
        as = new Socket(Protocol.SERVER, Protocol.PORT);
        sid = SID;
        aos = new ObjectOutputStream(as.getOutputStream());
        ais = new ObjectInputStream(as.getInputStream());
        aos.writeInt(Protocol.ASYNC);
        aos.writeObject(sid);
        aos.flush();
    }

    boolean condition = true;
    private Thread t;
    public void start(){
        t = new Thread(new Runnable() {
            public void run(){
                listen();
            }
        });
        condition = true;
        t.start();
    }

    public void stop(){
        condition = false;
    }

    public void listen() {
        int method;
        while (condition) {
            try {
                method = ais.readInt();
                switch (method) {
                    case Protocol.DELIVER_MESSAGE:
                        try {
                            String message = (String) ais.readObject();
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() { listener.deliver_message(message); }
                            });
                        } catch (ClassNotFoundException e) {System.out.println(e.getMessage());}
                        break;
                    case Protocol.USER_LOGIN:
                        try{
                            Usuario usuario = (Usuario) ais.readObject();
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() { listener.deliver_login(usuario); }
                            });
                        } catch (ClassNotFoundException e) {System.out.println(e.getMessage());}
                        break;
                    case Protocol.USER_LOGOUT:
                        try{
                            Usuario usuario = (Usuario) ais.readObject();
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() { listener.deliver_logout(usuario); }
                            });
                        } catch (Exception e) {System.out.println(e.getMessage());}
                        break;
                }
            } catch (IOException e) {
                condition = false;
            }
        }
        try {
            as.shutdownOutput();
            aos.close();
        } catch (IOException ex) { ex.getMessage();}
    }

//    private void deliver(final String message) {
//        SwingUtilities.invokeLater(new Runnable() {
//           public void run() {
//               listener.deliver_message(message);
//           }
//        });
//    }
}