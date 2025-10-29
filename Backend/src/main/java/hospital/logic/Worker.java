package hospital.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


public class Worker {
    Server srv;
    Socket socket;
    Service service;
    ObjectOutputStream os;
    ObjectInputStream is;

    String sid; // Session id
    Socket as; // Asynch Socket
    ObjectOutputStream aos;
    ObjectInputStream ais;

//    public Worker(Server srv, Socket socket, Service service) {
//        try{this.srv = srv;
//            this.socket = socket;
//            os = new ObjectOutputStream(socket.getOutputStream());
//            is = new ObjectInputStream(socket.getInputStream());
//            this.service = service;
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }

    public Worker(Server srv, Socket socket, ObjectOutputStream os, ObjectInputStream is, Service service, String sid) {
        this.srv = srv;
        this.socket = socket;
        this.os = os;
        this.is = is;
        this.service = service;
        this.sid = sid;
    }

    public void setAs(Socket as, ObjectOutputStream aos, ObjectInputStream ais) {
        this.as = as;
        this.ais = ais;
        this.aos = aos;
    }

    public void deliver_message(String message) {
        if(as != null){
            try{
                aos.writeInt(999 /*Implementar Protocol.DELIVER_MESSAGE*/);
                aos.writeObject(message);
                aos.flush();
            } catch (Exception e) { }
        }
    }

    boolean continuar;
    public void start(){
        try{
            System.out.println("Worker atendiendo peticiones...");
            Thread t = new Thread(
                    new Runnable() {
                        public void run() {listen();}
                    }
            );
            continuar = true;
            t.start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void stop(){
        continuar = false;
        System.out.println("Conexion cerrada...");
    }

    public void listen() {
        int method;
        while (continuar) {
            try {
                method = is.readInt();
                System.out.println("Operacion: " + method);
                switch (method) {


                    //Doctores

//
                    case Protocol.DOCTOR_CREATE:
                        try {
                            service.createDoctor((Doctor) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.DOCTOR_READ:
                        try {
                            Doctor doctor = service.readDoctor((Doctor) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(doctor);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.DOCTOR_UPDATE:
                        try {
                            System.out.println("PROTOCOL DOCTOR UPDATE: ");
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.DOCTOR_DELETE:
                        try {
                            service.deleteDoctor((Doctor) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.DOCTOR_SEARCH_NAME:
                        try{
                            List<Doctor> listDoctor = service.searchDoctorByName((Doctor) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listDoctor);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.DOCTOR_SEARCH_ID:
                        try{
                            System.out.println("PROTOCOL DOCTOR ID: ");
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        }
                        catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.DOCTOR_LOADALL:
                        try{
                            List<Doctor> listDoctor = service.loadListaDoctores();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listDoctor);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;


                    //Pacientes


                    case Protocol.PACIENTE_CREATE:
                        try{
                            service.createPaciente((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;

                    case Protocol.PACIENTE_READ:
                        try{
                            Paciente paciente = service.readPaciente((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(paciente);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PACIENTE_UPDATE:
                        try{
                            System.out.println("PROTOCOL PACIENTE UPDATE: ");
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PACIENTE_DELETE:
                        try{
                            service.deletePaciente((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH_NAME:
                        try{
                            List<Paciente> listaPaciente = service.searchPaciente((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listaPaciente);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PACIENTE_SEARCH_ID:
                        try{
                            List<Paciente> listaPaciente = service.searchPacienteID((Paciente) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listaPaciente);
                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PACIENTE_LOADALL:
                        try{
                            List<Paciente> listaPaciente = service.loadListaPacientes();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listaPaciente);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;


                    //Farmaceutas


                    case Protocol.FARMACEUTA_CREATE:
                        try{
                            service.createFarmaceuta((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.FARMACEUTA_READ:
                        try{
                            Farmaceuta farmaceuta = service.readFarmaceuta((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(farmaceuta);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.FARMACEUTA_UPDATE:
                        try{
                            System.out.println("PROTOCOL FARMACEUTA UPDATE: ");
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.FARMACEUTA_DELETE:
                        try{
                            service.deleteFarmaceuta((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.FARMACEUTA_SEARCH_NAME:
                        try{
                            List<Farmaceuta> listaFarmaceuta = service.searchFarmaceuta((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listaFarmaceuta);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.FARMACEUTA_SEARCH_ID:
                        try{
                            List<Farmaceuta> listaFarmaceuta = service.searchFarmaceutaID((Farmaceuta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listaFarmaceuta);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.FARMACEUTA_LOADALL:
                        try{
                            List<Farmaceuta>  listaFarmaceuta = service.loadListaFarmaceutas();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listaFarmaceuta);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    //Medicamentos


                    case Protocol.MEDICAMENTO_CREATE:
                        try{
                            service.createMedicamentos((Medicamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.MEDICAMENTO_READ:
                        try{
                            Medicamento medicamento = service.readMedicamentos((Medicamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(medicamento);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.MEDICAMENTO_UPDATE:
                        try{
                            System.out.println("PROTOCOL MEDICAMENTO UPDATE: ");
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.MEDICAMENTO_DELETE:
                        try{
                            service.deleteMedicamento((Medicamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH_NAME:
                        try{
                            List<Medicamento> listMedicamento = service.searchMedicamentoNombre((Medicamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listMedicamento);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.MEDICAMENTO_SEARCH_ID:
                        try{
                            List<Medicamento> listMedicamento = service.searchMedicamentoByCode((Medicamento) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listMedicamento);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.MEDICAMENTO_LOADALL:
                        try{
                            List<Medicamento>  listMedicamento = service.loadListaMedicamentos();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listMedicamento);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;

                    // DASHBOARD
                    case Protocol.DASHBOARD_GETPRESCRIPCIONES:
                        try{
                            System.out.println("PROTOCOL DASHBOARD GETPRESCRIPCIONES: ");
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;

                    //PRESCRIPCIONES

                    case Protocol.PRESCRIPCION_CREATE:
                        try{
                            service.createPrescripcion((Prescripcion) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PRESCRIPCION_READ:
                        try{
                            Prescripcion prescripcion = service.readPrescripcion((Prescripcion) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(prescripcion);
                        }catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PRESCRIPCION_UPDATE:
                        try{
                            service.actualizarPrescripcion((Prescripcion) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PRESCRIPCION_DELETE:
                        try{
                            System.out.println("PROTOCOL PRESCRIPCION_DELETE: ");
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PRESCRIPCION_SEARCH_ID:
                        try {
                            List<Prescripcion> listaPrescripcion = service.buscarPrescripciones((String) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listaPrescripcion);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PRESCRIPCION_LOADALL:
                        try{
                            List<Prescripcion> listaPrescripcion = service.loadListaPrescripciones();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listaPrescripcion);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.PRESCRIPCION_REMOVE_RECETA:
                        try{
                            service.removeRecetaFromPrescripcion((Prescripcion) is.readObject(), (Receta) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch(Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;


                    //Usuarios


                    case Protocol.USER_ADD:
                        try{
                            service.addUsuario((Usuario) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.USER_READ:
                        try {
                            Usuario recibido = (Usuario) is.readObject();
                            Usuario usuario = service.readUsuario(recibido);

                            System.out.println("Devolviendo al frontend: " + usuario.getClass().getName());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.reset();
                            os.writeObject(usuario);
                            os.flush();

                        } catch (Exception e) {
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.flush();
                            System.out.println("Error USER_READ: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;

                    case Protocol.USER_UPDATE:
                        try{
                            System.out.println("PROTOCOL USER UPDATE: ");
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.USER_DELETE:
                        try{
                            System.out.println("PROTOCOL USER DELETE: ");
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.USER_SEARCH_ID:
                        try{
                            Usuario usuario = service.searchUserID((String) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(usuario);
                        }  catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.USER_UPDATE_PASSWORD:
                        try{
                            service.updateClave((Usuario) is.readObject());
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.USER_LOADALL:
                        try{
                            List<Usuario> listUsuario = service.loadListaUsuarios();
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(listUsuario);
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.USER_LOGIN:
                        try{
                            Usuario usuario  = (Usuario) is.readObject();
                            // Use the login method which validates credentials and updates logged status
                            Usuario loggedUser = service.login(usuario);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(loggedUser);
                            System.out.println(loggedUser.getId() + " " + loggedUser.getUserType() +  " " + loggedUser.getLogged());
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.writeObject(e.getMessage()); // Send error message back
                            System.out.println(e.getMessage());
                        }
                        break;
                    case Protocol.USER_LOGOUT:
                        try{
                            Usuario usuario = (Usuario) is.readObject();
                            // Use the logout method instead of updateLogin
                            service.logout(usuario);
                            os.writeInt(Protocol.ERROR_NO_ERROR);
                            os.writeObject(usuario);
                            System.out.println("USER_LOGOUT - Usuario " + usuario.getId() + " ha cerrado sesión - Logged: " + usuario.getLogged());
                        } catch (Exception e){
                            os.writeInt(Protocol.ERROR_ERROR);
                            os.writeObject(e.getMessage()); // Send error message back
                            System.out.println("Error en USER_LOGOUT: " + e.getMessage());
                        }
//                        try{
//                            Usuario usuario  = (Usuario) is.readObject();
//                            usuario.setLogged(false);
//                            service.updateLogin(usuario);
//                            os.writeInt(Protocol.ERROR_NO_ERROR);
//                            os.writeObject(usuario);
//                            System.out.println(usuario.getId() + " " + usuario.getUserType() +  " " + usuario.getLogged());
//                        } catch (Exception e){
//                            os.writeInt(Protocol.ERROR_ERROR);
//                            System.out.println(e.getMessage());
//                        }
                        break;
                    case Protocol.DELIVER_MESSAGE:

                        break;
                }
                os.flush();
            } catch (IOException ex) {
                System.out.println(ex);
                stop();
            }
        }
    }

    //END OF CLASS
}
