package hospital.presentacion.login;

import hospital.logic.Protocol;
import hospital.logic.Usuario;
import hospital.logic.Service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Controller {

    View view;
    Model model;
    String sid;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        model.addPropertyChangeListener(view);

        establishAsyncConnection();
    }

    private void establishAsyncConnection() {
        try {
            Socket socket = new Socket(Protocol.SERVER, Protocol.PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Establish SYNC connection first to get session ID
            out.writeInt(Protocol.SYNC);
            out.flush();

            // Read session ID from server
            this.sid = (String) in.readObject();
            Service.instance().setSid(this.sid);

            System.out.println("Sync connection established, SID: " + this.sid);

            // Now establish ASYNC connection in a separate thread
            startAsyncListener();

        } catch (Exception e) {
            System.out.println("Error establishing connection: " + e.getMessage());
        }
    }

    private void startAsyncListener() {
        new Thread(() -> {
            try {
                // Create separate async socket
                Socket asyncSocket = new Socket(Protocol.SERVER, Protocol.PORT);
                ObjectOutputStream asyncOut = new ObjectOutputStream(asyncSocket.getOutputStream());
                ObjectInputStream asyncIn = new ObjectInputStream(asyncSocket.getInputStream());

                // Identify as async connection with session ID
                asyncOut.writeInt(Protocol.ASYNC);
                asyncOut.writeObject(this.sid);
                asyncOut.flush();

                System.out.println("Async connection established for session: " + this.sid);

                // Listen for async messages (login/logout notifications)
                listenForAsyncMessages(asyncIn);

            } catch (Exception e) {
                System.out.println("Error in async listener: " + e.getMessage());
            }
        }).start();
    }

    private void listenForAsyncMessages(ObjectInputStream asyncIn) {
        boolean running = true;
        while (running) {
            try {
                int method = asyncIn.readInt();
                switch (method) {
                    case Protocol.LOG_IN:
                        Usuario loggedInUser = (Usuario) asyncIn.readObject();
                        System.out.println("User logged in: " + loggedInUser.getId());
                        // Update model or show notification
                        break;
                    case Protocol.LOG_OUT:
                        Usuario loggedOutUser = (Usuario) asyncIn.readObject();
                        System.out.println("User logged out: " + loggedOutUser.getId());
                        // Update model or show notification
                        break;
                    case Protocol.DELIVER_MESSAGE:
                        String message = (String) asyncIn.readObject();
                        System.out.println("Message: " + message);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Async listener stopped: " + e.getMessage());
                running = false;
            }
        }
    }

    public Usuario login(Usuario usuario) throws Exception {
        // Buscar usuario en la lista
        Usuario u = Service.instance().readUsuario(usuario);

        // Validar clave
        if (!u.getClave().equals(usuario.getClave())) {
            throw new Exception("Clave incorrecta");
        }

        // Notify server about login using SYNC socket (since we need response)
        notifyServerLogin(u);

        return u;
    }

    private void notifyServerLogin(Usuario u) {
        try {
            // Use the existing sync connection to notify login
            Socket socket = new Socket(Protocol.SERVER, Protocol.PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Establish sync connection
            out.writeInt(Protocol.SYNC);
            out.flush();

            // Send login notification
            out.writeInt(Protocol.LOG_IN);
            out.writeObject(u);
            out.flush();

            // Read response
            int response = in.readInt();
            if (response == Protocol.ERROR_NO_ERROR) {
                System.out.println("Login broadcast successful");
            }

            out.close();
            in.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("ERROR al notificar el servidor: " + e.getMessage());
        }
    }

}
