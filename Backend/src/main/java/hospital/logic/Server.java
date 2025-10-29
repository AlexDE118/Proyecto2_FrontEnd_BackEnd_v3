package hospital.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    ServerSocket serverSocket;
    List<Worker> workers;

    public Server() {
        try {
            serverSocket = new ServerSocket(Protocol.PORT);
            workers = Collections.synchronizedList(new ArrayList<>());
            System.out.println("Server Started");
        } catch (IOException ex) {
            System.out.println("ERROR" + ex.getMessage());
            System.out.println(ex);
        }
    }

    public void run() {
        Service service = new Service();
        boolean continuar = true;
        Socket s;
        Worker worker;
        String sid; // Session ID;
        while (continuar) {
            try{
                s = serverSocket.accept();
                System.out.println("Connection Establecida...");
                ObjectInputStream is = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                int type = is.readInt();
                switch (type) {
                    case Protocol.SYNC:
                        sid = s.getRemoteSocketAddress().toString();
                        System.out.println("SYNC | SID: " + sid);
                        worker = new Worker(this, s, os, is, service, sid);
                        workers.add(worker);
                        System.out.println("Quedan: " + workers.size());
                        worker.start();
                        os.writeObject(sid);
                        break;
                    case Protocol.ASYNC:
                        sid = (String) is.readObject();
                        System.out.println("ASYNC | SID: " + sid);
                        join(s,os,is,sid);
                        break;

                }
                os.flush();
//                s = serverSocket.accept();
//                System.out.println( "Conexion Establecita... " + s.getInetAddress().getHostName());
//                ObjectInputStream ois = new ObjectOutputStream(s.getOutputStream());
//                worker = new Worker(this, s, service);
//                workers.add(worker);
//                System.out.println("Quedan: " + workers.size());
//                worker.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void join(Socket as, ObjectOutputStream aos, ObjectInputStream ais, String sid) {
        for(Worker worker : workers) {
            if(worker.sid.equals(sid)) {
                worker.setAs(as,aos,ais);
                break;
            }
        }
    }

    public void broadcastLogin(Usuario usuario) {
        synchronized(workers) {
            for(Worker worker : workers) {
                if(worker.as != null) { // Only send to clients with async connections
                    worker.deliver_login(usuario);
                }
            }
        }
    }

    public void broadcastLogout(Usuario usuario) {
        synchronized(workers) {
            for(Worker worker : workers) {
                if(worker.as != null) {
                    worker.deliver_logout(usuario);
                }
            }
        }
    }

    public void remove(Worker worker) {
        workers.remove(worker);
        System.out.println("Quedan: " + workers.size());
    }

//END OF CLASS
}
