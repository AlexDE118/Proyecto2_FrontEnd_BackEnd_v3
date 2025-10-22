package hospital.presentacion.paciente;

import hospital.logic.Paciente;
import hospital.presentacion.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Paciente current;
    List<Paciente> listaPacientes;

    public static final String CURRENT = "Current";
    public static final String LISTAPACIENTES = "Pacientes";

    public Model() {
        this.current = new Paciente();
        this.listaPacientes = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LISTAPACIENTES);
    }

    public Paciente getCurrent() {
        return current;
    }

    public void setCurrent(Paciente current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Paciente> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Paciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
        firePropertyChange(LISTAPACIENTES);
    }

    public void addPaciente(Paciente paciente) {
        listaPacientes.add(paciente);
        firePropertyChange(LISTAPACIENTES);
    }
}
