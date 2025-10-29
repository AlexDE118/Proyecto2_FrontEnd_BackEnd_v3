package hospital.logic;

import hospital.data.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//singleton

public class Service {
    private static Service instance;
//    private Listas listas;

    public static Service instance(){
        if(instance == null) instance = new Service();
        return instance;
    }

    private PacienteDAO pacienteDAO;
    private DoctorDAO doctorDAO;
    private FarmaceutaDAO farmaceutaDAO;
    private MedicamentoDAO medicamentDAO;
    private RecetaDAO recetaDAO;
    private PrescripcionDAO prescripcionDAO;
    private UsuarioDAO usuarioDAO;

    public Service(){
        try{
            //listas = XmlPersister.theInstance().load();
            pacienteDAO = new PacienteDAO();
            doctorDAO = new DoctorDAO();
            farmaceutaDAO = new FarmaceutaDAO();
            medicamentDAO = new MedicamentoDAO();
            recetaDAO = new RecetaDAO();
            prescripcionDAO = new PrescripcionDAO();
            usuarioDAO = new UsuarioDAO();

        } catch (Exception e){
            System.exit(-1);
            //listas = new Listas();
        }
    }

    public void stop(){
        try{
            //XmlPersister.theInstance().store(listas);
            Database.instance().close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    //================== DOCTORES ==================//

    public void createDoctor(Doctor doctor) throws Exception{
        doctorDAO.create(doctor);
    }

    public Doctor readDoctor(Doctor doctor) throws Exception {
        return doctorDAO.read(doctor.getId());
    }

    public List<Doctor> loadListaDoctores(){
        List<Doctor> doctors = new ArrayList<>();
        try{
        doctors = doctorDAO.findAll();
        } catch(Exception e){
            System.out.println(e);
        }
        return doctors;
    }

    public void deleteDoctor(Doctor doctor) throws Exception {
        doctorDAO.delete(doctor.getId());
    }

    public List<Doctor> searchDoctorByName(Doctor e) {
        List<Doctor> doctors = new ArrayList<>();
        try{
            doctors = doctorDAO.findByName(e.getNombre());
        } catch(Exception ex){
            System.out.println(ex);
        }
        return doctors;
    }
    //================== Pacientes ==================//

    public void createPaciente(Paciente paciente) throws Exception{
        pacienteDAO.create(paciente);
    }

    public Paciente readPaciente(Paciente paciente) throws Exception {
        return pacienteDAO.read(paciente.getId());
    }

    public List<Paciente> loadListaPacientes(){
        return pacienteDAO.findAll();
    }

    public void deletePaciente(Paciente paciente) throws Exception {
        pacienteDAO.delete(paciente);
    }

    public List<Paciente> searchPaciente(Paciente paciente) {
        return pacienteDAO.findByName(paciente);
    }

    public List<Paciente> searchPacienteID(Paciente paciente) {
        return pacienteDAO.findByID(paciente);
    }

    //================== Farmaceutas ==================//

    public void createFarmaceuta(Farmaceuta farmaceuta) throws Exception{
        farmaceutaDAO.create(farmaceuta);
//        }
    }

    public Farmaceuta readFarmaceuta(Farmaceuta farmaceuta) throws Exception{
        Farmaceuta result = farmaceutaDAO.read(farmaceuta.getId());
        if(result != null){
            return result;
        }else {
            throw new Exception("Farmaceuta no existente");
        }
    }

    public void deleteFarmaceuta(Farmaceuta farmaceuta) throws Exception {
        farmaceutaDAO.delete(farmaceuta);
    }

    public List<Farmaceuta> loadListaFarmaceutas(){
        List<Farmaceuta> farmaceutas = new ArrayList<>();
        try {
            farmaceutas = farmaceutaDAO.findAll();
        } catch (Exception e) { }

        return farmaceutas;
    }

    public List<Farmaceuta> searchFarmaceuta(Farmaceuta farmaceuta) {//Nombre
        List<Farmaceuta> farmaceutas = new ArrayList<>();
        try {
            farmaceutas = farmaceutaDAO.findByName(farmaceuta.getNombre());
        } catch (Exception e) { }

        return farmaceutas;
    }

    public List<Farmaceuta> searchFarmaceutaID(Farmaceuta farmaceuta) {//ID
        List<Farmaceuta> farmaceutas = new ArrayList<>();
        try {
                farmaceutas = farmaceutaDAO.findByName(farmaceuta.getId());
        } catch (Exception e) { }

        return farmaceutas;
    }

    //================== Medicamentos ==================//

    public void createMedicamentos(Medicamento medicamento) throws Exception{
        medicamentDAO.create(medicamento);
    }

    public Medicamento readMedicamentos(Medicamento medicamento) throws Exception {
        Medicamento result = medicamentDAO.read(medicamento.getCodigo());
        if(result != null){
            return result;
        } else   {
            throw new Exception("Medicamento no existente");
        }
    }

    public List<Medicamento> loadListaMedicamentos(){
        List <Medicamento> result = new ArrayList<>();
        try{
            result = medicamentDAO.findAll();
        } catch(Exception e){}
        return result;
    }

    public List<Medicamento> searchMedicamento(Medicamento medicamento) { //ID
        List<Medicamento> medicamentos = new ArrayList<>();
        try {
            medicamentos = medicamentDAO.findByName(medicamento.getNombre());
        } catch (Exception e) {

        }
        return medicamentos;
    }

    public void deleteMedicamento(Medicamento medicamento) throws Exception {
        medicamentDAO.delete(medicamento);
//        }
    }

    public List<Medicamento> searchMedicamentoByCode(Medicamento e) {
        List<Medicamento> medicamentos = new ArrayList<>();
        try {
            medicamentos = medicamentDAO.findByID(e.getCodigo());
        } catch (Exception ex) {

        }
        return medicamentos;
    }

    public List<Medicamento> searchMedicamentoNombre(Medicamento medicamento) {
        List<Medicamento> medicamentos = new ArrayList<>();
        try {
            medicamentos = medicamentDAO.findByName(medicamento.getNombre());
        } catch (Exception e) {

        }
        return medicamentos;
    }
    //========================== Receta ==========================//

    public void createReceta(Receta receta) {
        try {
            recetaDAO.create(receta);
        }  catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Receta>  loadListaRecetas(){
        List<Receta> recetas = new ArrayList<>();
        return recetas;
    }

    public void removeReceta(Receta receta) {
        try {
            recetaDAO.delete(receta);
        }   catch (Exception e) {
            System.out.println(e);
        }
        //listas.getRecetas().remove(receta);
    }

    //========================== Prescripcion ==========================//

    public void createPrescripcion(Prescripcion prescripcion) throws Exception{
        prescripcionDAO.create(prescripcion);
    }

    public Prescripcion readPrescripcion(Prescripcion prescripcion) throws Exception{
        Prescripcion result = prescripcionDAO.read(prescripcion.getPaciente().getId());
        if(result != null){
            return result;
        } else {
            throw new Exception("Prescripcion no existente");
        }
    }

    public List<Prescripcion> loadListaPrescripciones(){
        List<Prescripcion> prescripciones = new ArrayList<>();
        try{

        } catch (Exception e) { }
        return prescripciones;
    }

//    public void updatePrescripcion(Prescripcion prescripcion) throws Exception {
//        prescripcionDAO.update(prescripcion);
//    }

    public List<Prescripcion> buscarPrescripciones(String criterio) {
        List<Prescripcion> resultado = new ArrayList<>();
        return resultado;
    }

    public void actualizarPrescripcion(Prescripcion prescripcion) {
        try {
            prescripcionDAO.update(prescripcion);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeRecetaFromPrescripcion(Prescripcion prescripcion, Receta receta) throws Exception {
        if (!prescripcion.getReceta().remove(receta)) {
            throw new Exception("La receta no existe en esta prescripción");
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
            usuarios = usuarioDAO.findAll();
        } catch (Exception e) {
            System.out.println("Error al obtener los usuarios");
        }
        return usuarios;
    }

    public Usuario readUsuario(Usuario usuario) throws Exception{
        System.out.println("Buscando usuario con ID: '" + usuario.getId() + "'");
        Usuario result = usuarioDAO.read(usuario);
        if(result != null){
            System.out.println("Clase devuelta por DAO: " + result.getClass().getName());
            return result;
        }else{
            throw new Exception("Usuario no existe");
        }
    }

    public void addUsuario(Usuario usuario) {
        try{
            usuarioDAO.create(usuario);
        } catch (Exception ex) {
            System.out.println("Error al agregar usuario: " + ex.getMessage());
        }
    }

    public void removeUsuario(Usuario usuario) {
        try{
            usuarioDAO.delete(usuario);
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    public Usuario searchUserID(String id) throws Exception {
       Usuario usuario = new Usuario();
        try{
        //    usuario = usuarioDAO.read(id);
       }  catch (Exception e) {

       }
        return usuario;
    }

    public Usuario searchUser(Usuario usuario) throws Exception {
        Usuario result = new Usuario();
        try{
            usuario = usuarioDAO.read(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<Usuario> searchUsers(Usuario usuario){
        return usuarioDAO.findByID(usuario);
    }

    public void sendMessage(Usuario usuario, String message) throws Exception {
        String msg = "["+usuario.getId()+ "]: " + message;
        usuario.getMensajes().add(msg);
        usuarioDAO.update(usuario);
    }

    public void updateClave(Usuario usuario) throws Exception {
        Usuario result = usuarioDAO.findByID(usuario).get(1);

        if (result != null) {
            result.setClave(usuario.getClave());
            stop();
        } else {
            throw new Exception("Usuario no encontrado para actualizar clave");
        }
    }
    //======================= END ======================//
}

