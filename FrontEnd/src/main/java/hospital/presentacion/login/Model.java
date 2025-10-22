package hospital.presentacion.login;

import hospital.logic.Usuario;
import hospital.presentacion.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    Usuario current;
    List<Usuario> listaUsuarios;

    public static final String CURRENT = "current";
    public static final String LISTA_USUARIOS = "listaUsuarios";

    public Model() {
        listaUsuarios = new ArrayList<>();
        current = new Usuario();
    }

    public void setCurrent(Usuario current) {
        this.current = current;
    }
    public Usuario getCurrent() {
        return current;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LISTA_USUARIOS);
    }
}
