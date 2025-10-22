package hospital.presentacion.prescripcion;

import hospital.logic.Medicamento;
import hospital.logic.Paciente;
import hospital.logic.Prescripcion;
import hospital.logic.Receta;
import hospital.logic.Service;

import javax.swing.*;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);

        List<Paciente> allPatients = Service.instance().loadListaPacientes();
        System.out.println("Loaded " + allPatients.size() + " patients");
        model.setPacientes(allPatients);
    //    model.setPacientes(Service.instance().searchPaciente(new Paciente()));
    }

    public void createPrescripcion(Prescripcion prescripcion) throws Exception {
        prescripcion.setPaciente(model.current.getPaciente());
        Service.instance().createPrescripcion(prescripcion);
        model.setCurrent(new Prescripcion());
        model.setPacientes(Service.instance().loadListaPacientes());
    }

    public void clear(){
        model.setCurrent(new Prescripcion());
    }

    //Pacientes

    public void setPaciente(int row){
        model.setPaciente(model.getPacientes().get(row));
    }

    public void searchPacientes(String nombre){
        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        model.setPacientes(Service.instance().searchPaciente(paciente));
    }

    public void searchPacientesid(String id){
        Paciente paciente = new Paciente();
        paciente.setId(id);
        model.setPacientes(Service.instance().searchPacienteID(paciente));
    }

    //Medicamentos

    public void searchMedicamentoNombre(String nombre){
        Medicamento medicamento = new Medicamento();
        medicamento.setNombre(nombre);
        model.setMedicamentos(Service.instance().searchMedicamentoNombre(medicamento));
    }

    public void searchMedicamentoID(String codigo){
        Medicamento medicamento = new Medicamento();
        medicamento.setCodigo(codigo);
        model.setMedicamentos(Service.instance().searchMedicamentoByCode(medicamento));
    }

    public void loadRecetas(){
        model.setRecetas(Service.instance().loadListaRecetas());
    }

    public void loadMedicamentos(){ model.setMedicamentos(Service.instance().loadListaMedicamentos()); }

    public void removeRecetaFromPrescripcion(Prescripcion prescripcion, Receta receta) {
        try {
            Service.instance().removeRecetaFromPrescripcion(prescripcion, receta);
            model.setRecetas(prescripcion.getReceta()); // refresca el modelo
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al descartar medicamento: " + e.getMessage());
        }
    }

}
