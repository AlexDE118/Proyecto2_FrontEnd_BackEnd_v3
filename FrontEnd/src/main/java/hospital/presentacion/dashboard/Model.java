package hospital.presentacion.dashboard;

import hospital.logic.Prescripcion;
import hospital.presentacion.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Prescripcion current;
    List<Prescripcion> listaPrescripcion;


    public static final String CURRENT = "current";
    public static final String LISTAPRESCRIPCIONES = "listaPrescripcion";

    public Model() {
        current = new Prescripcion();
        listaPrescripcion = new ArrayList<>();
    }

    public Prescripcion getCurrent() {
        return current;
    }

    public void setCurrent(Prescripcion current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Prescripcion> getListaPrescripcion() {
        return listaPrescripcion;
    }

    public void setListaPrescripcion(List<Prescripcion> listaPrescripcion) {
        this.listaPrescripcion = listaPrescripcion;
        firePropertyChange(LISTAPRESCRIPCIONES);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LISTAPRESCRIPCIONES);
    }



}
