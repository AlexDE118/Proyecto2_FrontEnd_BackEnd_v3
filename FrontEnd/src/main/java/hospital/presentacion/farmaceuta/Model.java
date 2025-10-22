package hospital.presentacion.farmaceuta;

import hospital.logic.Farmaceuta;
import hospital.presentacion.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Farmaceuta current;
    List<Farmaceuta> listaFarmaceuta;

    public static final String CURRENT = "current";
    public static final String LISTAFARMACEUTAS = "listaFarmaceuta";

    public Model() {
        current = new Farmaceuta();
        listaFarmaceuta = new ArrayList<>();
    }

    public Farmaceuta getCurrent() {
        return current;
    }

    public void setCurrent(Farmaceuta current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Farmaceuta> getListaFarmaceuta() {
        return listaFarmaceuta;
    }

    public void setListaFarmaceuta(List<Farmaceuta> listaFarmaceuta) {
        this.listaFarmaceuta = listaFarmaceuta;
        firePropertyChange(LISTAFARMACEUTAS);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LISTAFARMACEUTAS);
    }

}
