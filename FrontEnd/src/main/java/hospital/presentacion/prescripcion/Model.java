package hospital.presentacion.prescripcion;

import hospital.logic.Medicamento;
import hospital.logic.Paciente;
import hospital.logic.Prescripcion;
import hospital.logic.Receta;
import hospital.presentacion.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Prescripcion current;
    List<Prescripcion> prescripciones;
    List<Receta> recetas;
    List<Paciente>  pacientes;
    Medicamento medicamentoCurrent;
    List<Medicamento>  medicamentos;

    public static final String CURRENT = "current";
    public static final String RECETAS = "recetas";
    public static final String PACIENTS = "pacientes";
    public static final String PACIENT = "paciente";
    public static final String MEDICAMENTOS = "medicamentos";
    public static final String MEDICAMENTO = "medicamento";

    public Model() {
        current = new Prescripcion();
        pacientes = new ArrayList<Paciente>();
        medicamentos = new ArrayList<Medicamento>();
        recetas = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(RECETAS);
        firePropertyChange(PACIENTS);
        firePropertyChange(PACIENT);
        firePropertyChange(MEDICAMENTOS);
        firePropertyChange(MEDICAMENTO);
    }

    public Prescripcion getCurrent() {
        return current;
    }

    public void setCurrent(Prescripcion current) {
        this.current = current;
        firePropertyChange(CURRENT);
        firePropertyChange(PACIENT);
        // Also fire recetas change when current changes
        if (current != null && current.getReceta() != null) {
            this.recetas = current.getReceta();
            firePropertyChange(RECETAS);
        }
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
        firePropertyChange(RECETAS);
    }

    public Medicamento getMedicamentoCurrent() {
        return medicamentoCurrent;
    }

    public void setMedicamentoCurrent(Medicamento medicamentoCurrent) {
        this.medicamentoCurrent = medicamentoCurrent;
        firePropertyChange(MEDICAMENTO);
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes){
        this.pacientes = pacientes;
        firePropertyChange(PACIENTS);
    }

    public void setPaciente(Paciente paciente){
        this.current.setPaciente(paciente);
        firePropertyChange(PACIENT);
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos){
        this.medicamentos = medicamentos;
        firePropertyChange(MEDICAMENTOS);
    }

    public void setMedicamento(Medicamento medicamento){
        this.current.addMedicamentoToReceta(medicamento);
        firePropertyChange(MEDICAMENTO);
    }

}
