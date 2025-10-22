package hospital.logic;

import java.io.IOException;
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
        while (continuar) {
            try{
                s = serverSocket.accept();
                System.out.println( "Conexion Establecita... " + s.getInetAddress().getHostName());
                worker = new Worker(this, s, service);
                workers.add(worker);
                System.out.println("Quedan: " + workers.size());
                worker.start();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void remove(Worker worker) {
        workers.remove(worker);
        System.out.println("Quedan: " + workers.size());
    }

//END OF CLASS
}
