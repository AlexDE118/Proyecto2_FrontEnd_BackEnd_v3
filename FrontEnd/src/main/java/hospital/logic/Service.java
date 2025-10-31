package hospital.logic;

//import hospital.data.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//singleton

public class Service {
    private static Service instance;
    public static Service instance(){
        if(instance == null) instance = new Service();
        return instance;
    }

    private Socket s;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    String sid; // Session ID;

    private Service(){
        try{
            s = new Socket(Protocol.SERVER, Protocol.PORT);
            os = new ObjectOutputStream(s.getOutputStream());
            is = new ObjectInputStream(s.getInputStream());

            os.writeInt(Protocol.SYNC);
            os.flush();
            sid = (String) is.readObject(); //Stores returned Session ID;

        } catch (Exception e){
            System.exit(-1);
        }
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    private void disconnect() throws Exception {
        os.writeInt(Protocol.DISCONNECT);
        os.flush();
        s.shutdownOutput();
        s.close();
    }

    public void stop(){
        try{
            disconnect();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    //================== DOCTORES ==================//

    public void createDoctor(Doctor doctor) throws Exception{
        os.writeInt(Protocol.DOCTOR_CREATE);
        os.writeObject(doctor);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Doctor creado");
        }
        else throw new Exception("El doctor ya existe");
    }

    public Doctor readDoctor(Doctor doctor) throws Exception {
        os.writeInt(Protocol.DOCTOR_READ);
        os.writeObject(doctor);
        os.flush();
        if(is.readInt() == Protocol.ERROR_NO_ERROR) return (Doctor) is.readObject();
        else throw new Exception("Doctor no existe");
    }

    public void updateDoctor(Doctor doctor) throws Exception {
        os.writeInt(Protocol.DOCTOR_UPDATE);
        os.writeObject(doctor);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Doctor actualizado exitosamente");
        } else {
            throw new Exception("Error al actualizar el doctor");
        }
    }

    public List<Doctor> loadListaDoctores(){
        List<Doctor> doctors = new ArrayList<>();
        try{
        os.writeInt(Protocol.DOCTOR_LOADALL);
        os.flush();
        if(is.readInt() == Protocol.ERROR_NO_ERROR) doctors = (List<Doctor>) is.readObject();
        else return List.of();
        } catch(Exception e){
            System.out.println(e);
        }
        return doctors;
    }

    public void deleteDoctor(Doctor doctor) throws Exception {
        os.writeInt(Protocol.DOCTOR_DELETE);
        os.writeObject(doctor);
        os.flush();
        if(is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Doctor Eliminado");
        }
        else throw new Exception("Doctor no existe");
    }

    public List<Doctor> searchByID(Doctor doctor) {
        try{
            os.writeInt(Protocol.DOCTOR_SEARCH_ID);
            os.writeObject(doctor);
            os.flush();
            if(is.readInt() == Protocol.ERROR_NO_ERROR) return (List<Doctor>) is.readObject();
            else return List.of();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public List<Doctor> searchDoctorByName(Doctor doctor) {
        try{
            os.writeInt(Protocol.DOCTOR_SEARCH_NAME);
            os.writeObject(doctor);
            os.flush();
            if(is.readInt() == Protocol.ERROR_NO_ERROR) return (List<Doctor>) is.readObject();
            else return List.of();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    //================== Pacientes ==================//

    public void createPaciente(Paciente paciente) throws Exception{
        os.writeInt(Protocol.PACIENTE_CREATE);
        os.writeObject(paciente);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Paciente creado");
        }
        else throw new Exception("Error al crear paciente");
    }

    public Paciente readPaciente(Paciente paciente) throws Exception {
        os.writeInt(Protocol.PACIENTE_READ);
        os.writeObject(paciente);
        os.flush();
        if(is.readInt() == Protocol.ERROR_NO_ERROR) return (Paciente) is.readObject();
        else throw new Exception("Paciente no existe");
    }

    public void updatePaciente(Paciente paciente) throws Exception {
        os.writeInt(Protocol.PACIENTE_UPDATE);
        os.writeObject(paciente);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Paciente actualizado exitosamente");
        } else  {
            throw new Exception("Front end - Error al actualizar paciente");
        }
    }

    public List<Paciente> loadListaPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        try {
            os.writeInt(Protocol.PACIENTE_LOADALL);
            os.flush();
            if(is.readInt() == Protocol.ERROR_NO_ERROR) pacientes = (List<Paciente>) is.readObject();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            //throw new RuntimeException(ex);
        }
        return pacientes;
    }

    public void deletePaciente(Paciente paciente) throws Exception {
        os.writeInt(Protocol.PACIENTE_DELETE);
        os.writeObject(paciente);
        os.flush();
        if(is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Paciente Eliminado");
        }
        else throw new Exception("Paciente no existe");
    }

    public List<Paciente> searchPaciente(Paciente paciente) {
        List<Paciente> pacientes = new ArrayList<>();
        try{
            os.writeInt(Protocol.PACIENTE_SEARCH_NAME);
            os.writeObject(paciente);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) pacientes = (List<Paciente>) is.readObject();
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return  pacientes;
    }

    public List<Paciente> searchPacienteID(Paciente paciente) {
        List<Paciente> pacientes = new ArrayList<>();
        try{
            os.writeInt(Protocol.PACIENTE_SEARCH_ID);
            os.writeObject(paciente);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) pacientes = (List<Paciente>) is.readObject();
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return  pacientes;
    }

    //================== Farmaceutas ==================//

    public void createFarmaceuta(Farmaceuta farmaceuta) throws Exception{
        os.writeInt(Protocol.FARMACEUTA_CREATE);
        os.writeObject(farmaceuta);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Farmaceuta creado");
        }
        else throw new Exception("Farmaceuta no existe");
    }

    public Farmaceuta readFarmaceuta(Farmaceuta farmaceuta) throws Exception{
        os.writeInt(Protocol.FARMACEUTA_READ);
        os.writeObject(farmaceuta);
        os.flush();
        if(is.readInt() == Protocol.ERROR_NO_ERROR)
            return (Farmaceuta) is.readObject();
        else throw new Exception("Farmaceuta no existe");
    }

    public void updateFarmaceuta(Farmaceuta farmaceuta) throws Exception{
        os.writeInt(Protocol.FARMACEUTA_UPDATE);
        os.writeObject(farmaceuta);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Farmaceuta actualizado");
        } else throw new Exception("Error al actualizar farmaceuta");
    }

    public void deleteFarmaceuta(Farmaceuta farmaceuta) throws Exception {
        os.writeInt(Protocol.FARMACEUTA_DELETE);
        os.writeObject(farmaceuta);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Farmaceuta Eliminado");
        }
        else throw new Exception("Farmaceuta Eliminado");
    }

    public List<Farmaceuta> loadListaFarmaceutas(){
        List<Farmaceuta> farmaceutas = new ArrayList<>();
        try {
            os.writeInt(Protocol.FARMACEUTA_LOADALL);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                farmaceutas = (List<Farmaceuta>) is.readObject();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return farmaceutas;
    }

    public List<Farmaceuta> searchFarmaceuta(Farmaceuta farmaceuta) {//Nombre
        List<Farmaceuta> farmaceutas = new ArrayList<>();
        try {
            os.writeInt(Protocol.FARMACEUTA_SEARCH_NAME);
            os.writeObject(farmaceuta);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                farmaceutas = (List<Farmaceuta>) is.readObject();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return farmaceutas;
    }

    //================== Medicamentos ==================//

    public void createMedicamentos(Medicamento medicamento) throws Exception{
        os.writeInt(Protocol.MEDICAMENTO_CREATE);
        os.writeObject(medicamento);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Medicamento creado");
        }
        else throw new Exception("Medicamento no existe");
    }

    public Medicamento readMedicamentos(Medicamento medicamento) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_READ);
        os.writeObject(medicamento);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            return (Medicamento) is.readObject();
        }
        else throw new Exception("Medicamento no existe");
    }

    public void updateMedicamentos(Medicamento medicamento) throws Exception{
        os.writeInt(Protocol.MEDICAMENTO_UPDATE);
        os.writeObject(medicamento);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Medicamento actualizado");
        }
        else throw new Exception("Medicamento no existe");
    }

    public List<Medicamento> loadListaMedicamentos(){
        List <Medicamento> result = new ArrayList<>();
        try{
            os.writeInt(Protocol.MEDICAMENTO_LOADALL);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                result = (List<Medicamento>) is.readObject();
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<Medicamento> searchMedicamento(Medicamento medicamento) { //ID
        List<Medicamento> medicamentos = new ArrayList<>();
        try {
            os.writeInt(Protocol.MEDICAMENTO_SEARCH_NAME);
            os.writeObject(medicamento);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                medicamentos = (List<Medicamento>) is.readObject();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return medicamentos;
    }

    public void deleteMedicamento(Medicamento medicamento) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DELETE);
        os.writeObject(medicamento);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Medicamento eliminado");
        }
        else throw new Exception("Medicamento no existe");
    }

    public List<Medicamento> searchMedicamentoByCode(Medicamento e) {
        List<Medicamento> medicamentos = new ArrayList<>();
        try {
            os.writeInt(Protocol.MEDICAMENTO_SEARCH_ID);
            os.writeObject(e);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                medicamentos = (List<Medicamento>) is.readObject();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return medicamentos;
    }

    public List<Medicamento> searchMedicamentoNombre(Medicamento medicamento) {
        List<Medicamento> medicamentos = new ArrayList<>();
        try {
            os.writeInt(Protocol.MEDICAMENTO_SEARCH_NAME);
            os.writeObject(medicamento);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                medicamentos = (List<Medicamento>) is.readObject();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return medicamentos;
    }
    //========================== Receta ==========================//

    public void createReceta(Receta receta) throws Exception {
        os.writeInt(Protocol.RECETA_CREATE);
        os.writeObject(receta);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Receta creada");
        }
        else throw new Exception("Receta no existe");
    }

    public List<Receta>  loadListaRecetas(){
        List<Receta> recetas = new ArrayList<>();
        try{
            os.writeInt(Protocol.RECETA_LOADALL);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                recetas = (List<Receta>) is.readObject();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return recetas;
    }

    public void removeReceta(Receta receta) {
        try {
            os.writeInt(Protocol.RECETA_DELETE);
            os.writeObject(receta);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                System.out.println("Receta eliminada");
            }
        }   catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    //========================== Prescripcion ==========================//

    public void createPrescripcion(Prescripcion prescripcion) throws Exception{
        os.writeInt(Protocol.PRESCRIPCION_CREATE);
        os.writeObject(prescripcion);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Prescripcion creada");
        }
        else throw new Exception("Prescripcion ya existe");
    }

    public Prescripcion readPrescripcion(Prescripcion prescripcion) throws Exception{
        os.writeInt(Protocol.PRESCRIPCION_READ);
        os.writeObject(prescripcion);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            return (Prescripcion) is.readObject();
        }
        else throw new Exception("Prescripcion no existe");
    }

    public List<Prescripcion> loadListaPrescripciones(){
        List<Prescripcion> prescripciones = new ArrayList<>();
        try{
            os.writeInt(Protocol.PRESCRIPCION_LOADALL);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                prescripciones = (List<Prescripcion>) is.readObject();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return prescripciones;
    }

    public void updatePrescripcion(Prescripcion prescripcion) throws Exception {
        os.writeInt(Protocol.PRESCRIPCION_UPDATE);
        os.writeObject(prescripcion);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Prescripcion actualizada");
        }
        else throw new Exception("Prescripcion no existe");
    }

    public List<Prescripcion> buscarPrescripciones(String criterio) {
        List<Prescripcion> resultado = new ArrayList<>();
        try{
            os.writeInt(Protocol.PRESCRIPCION_SEARCH_ID);
            os.writeObject(criterio);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                resultado = (List<Prescripcion>) is.readObject();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public void actualizarPrescripcion(Prescripcion prescripcion) {
        try {
            os.writeInt(Protocol.PRESCRIPCION_UPDATE);
            os.writeObject(prescripcion);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                System.out.println("Prescripcion actualizada");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeRecetaFromPrescripcion(Prescripcion prescripcion, Receta receta) throws Exception {
        os.writeInt(Protocol.PRESCRIPCION_REMOVE_RECETA);
        os.writeObject(prescripcion);
        os.writeObject(receta);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            System.out.println("Prescripcion eliminada");
        }
    }

    //======================= DASHBOARD ======================//
    public List<Prescripcion> getPrescripciones(LocalDate desde, LocalDate hasta, String medicamento) {
        List<Prescripcion> resultado = new ArrayList<>();
//        listas.getPrescripciones().forEach(p ->
//                System.out.println("Prescripcion: " + p.getPaciente().getNombre() + ", " +
//                        "Fecha: " + p.getFechaRetiro() + ", Recetas: " +
//                        p.getReceta().stream().map(r -> r.getMedicamentos().getNombre()).toList())
//        );
//
//        return listas.getPrescripciones().stream()
//                .filter(p -> !p.getFechaRetiro().isBefore(desde) && !p.getFechaRetiro().isAfter(hasta))
//                .filter(p -> medicamento == null || medicamento.isBlank() || medicamento.equalsIgnoreCase("Todos")
//                        || p.getReceta().stream()  // recorremos la lista de recetas
//                        .anyMatch(r -> r.getMedicamentos().getNombre().equalsIgnoreCase(medicamento)))
//                .collect(Collectors.toList());
        return resultado;
    }

    //======================= USUARIO =======================//

    public List<Usuario> loadListaUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        try{
            os.writeInt(Protocol.USER_LOADALL);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                usuarios = (List<Usuario>) is.readObject();
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los usuarios");
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    public Usuario readUsuario(Usuario usuario) throws Exception {
        System.out.println("Buscando usuario con ID: '" + usuario.getId() + "'");
        os.writeInt(Protocol.USER_READ);
        os.writeObject(usuario);
        os.flush();

        int status = is.readInt();
        System.out.println("Código de respuesta del servidor: " + status);

        if (status == Protocol.ERROR_NO_ERROR) {
            Object recibido = is.readObject();
            System.out.println("Objeto recibido del backend: " + recibido.getClass().getName());

            if (recibido instanceof Usuario) {
                Usuario u = (Usuario) recibido;
                System.out.println("Usuario leído correctamente: " + u.getId() + " - Tipo: " + u.getUserType());
                return u;
            } else {
                System.out.println("El backend devolvió un tipo inesperado: " + recibido.getClass().getName());
                throw new Exception("Tipo de objeto inesperado (" + recibido.getClass().getName() + ")");
            }

        } else {
            throw new Exception("Usuario no existe");
        }
    }


    public void addUsuario(Usuario usuario) {
        try {
            os.writeInt(Protocol.USER_ADD);
            os.writeObject(usuario);
            os.flush();
            if (is.readInt() == Protocol.ERROR_NO_ERROR) {
                System.out.println("Usuario adicionado");
            }
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public Usuario searchUserID(String id) throws Exception {
        Usuario usuario = new Usuario();
        os.writeInt(Protocol.USER_SEARCH_ID);
        os.writeObject(id);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            usuario = (Usuario) is.readObject();
            return  usuario;
        } else throw new Exception("Usuario no existe");
    }

    public void updateClave(Usuario usuario) throws Exception {
        Usuario result = new Usuario();
        os.writeInt(Protocol.USER_UPDATE_PASSWORD);
        os.writeObject(usuario);
        os.flush();
        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            result = (Usuario) is.readObject();
            result.setClave(usuario.getClave());
        }
        else {
            throw new Exception("Usuario no encontrado para actualizar clave");
        }
    }

    public Usuario login(Usuario usuario) throws Exception {
        //FRONT END
        os.writeInt(Protocol.USER_LOGIN);
        os.writeObject(usuario);
        os.flush();

        int respuesta = is.readInt();
        if (respuesta == Protocol.ERROR_NO_ERROR) {
            Usuario result = (Usuario) is.readObject();
            System.out.println("Login exitoso para usuario: " + usuario.getId());
            return result;
        } else {
            throw new Exception("Error durante el login");
        }
    }



    public void logout(Usuario usuario) throws Exception {
        os.writeInt(Protocol.USER_LOGOUT);
        os.writeObject(usuario);
        os.flush();

        if (is.readInt() == Protocol.ERROR_NO_ERROR) {
            Usuario result = (Usuario) is.readObject();
            System.out.println("Logout exitoso para usuario: " + usuario.getId() + " - logged: " + usuario.getLogged());
        } else {
            throw new Exception("Error durante el logout");
        }
    }

    //======================= END ======================//
}

