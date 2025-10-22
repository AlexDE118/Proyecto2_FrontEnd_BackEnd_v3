package hospital.presentacion.despacho;

import hospital.logic.Prescripcion;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Model {
    public static final String PRESCRIPCIONES = "prescripciones";
    public static final String SELECCIONADA = "seleccionada";

    private List<Prescripcion> prescripciones;
    private Prescripcion seleccionada;

    PropertyChangeSupport support;

    public Model() {
        this.prescripciones = new ArrayList<>();
        this.seleccionada = null;
        this.support = new PropertyChangeSupport(this);
    }

    public void setPrescripciones(List<Prescripcion> prescripciones) {
        this.prescripciones = prescripciones;
        support.firePropertyChange(PRESCRIPCIONES, null, prescripciones);
    }

    public List<Prescripcion> getPrescripciones() {
        return prescripciones;
    }

    public void setSeleccionada(Prescripcion seleccionada) {
        this.seleccionada = seleccionada;
        support.firePropertyChange(SELECCIONADA, null, seleccionada);
    }

    public Prescripcion getSeleccionada() {
        return seleccionada;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }
}
