package hospital.presentacion.historico;

import hospital.logic.Prescripcion;
import hospital.logic.Receta;
import hospital.presentacion.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Prescripcion current;
    List<Prescripcion> prescripciones;
    List<Receta>  recetas;

    public static final String CURRENT = "current";
    public static final String PRESCRIPCIONES = "prescripciones";
    public static final String RECENTAS = "recetas";

    public Model() {
        this.current = new Prescripcion();
        this.prescripciones = new ArrayList<Prescripcion>();
        this.recetas = new ArrayList<Receta>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(PRESCRIPCIONES);
        firePropertyChange(RECENTAS);
    }

    public Prescripcion getCurrent() {
        return current;
    }

    public void setCurrent(Prescripcion current) {
        this.current = current;
        firePropertyChange(CURRENT);
        firePropertyChange(RECENTAS);
    }

    public List<Prescripcion> getPrescripciones() {
        return prescripciones;
    }

    public void setPrescripciones(List<Prescripcion> prescripciones) {
        this.prescripciones = prescripciones;
        firePropertyChange(PRESCRIPCIONES);
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
        firePropertyChange(RECENTAS);
    }

    public void setListaRecetas(List<Receta> recetas) {
        this.current.setReceta(recetas);
        firePropertyChange(RECENTAS);
    }
}
