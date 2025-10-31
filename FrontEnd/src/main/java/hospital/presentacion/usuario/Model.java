package hospital.presentacion.usuario;

import hospital.logic.Usuario;
import hospital.presentacion.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
Usuario filter;
Usuario current;
Usuario currentLoggedUser;
List<Usuario> usuarios;
List<Usuario> loggedUsers;
int mode;

public static final String CURRENT = "current";
public static final String LISTAUSUARIOS = "listaUsuarios";
public static final String FILTER = "filter";
public static final String LOGGEDUSERS = "loggedUsers";
public static final String CURRENTLOGGEDUSER = "currentLoggedUser";

public Model() {
    loggedUsers = new ArrayList<>();
}

public void init() {
    filter = new Usuario();
    current = new Usuario();
}

@Override
public void addPropertyChangeListener(PropertyChangeListener listener) {
    super.addPropertyChangeListener(listener);
    firePropertyChange(CURRENT);
    firePropertyChange(LISTAUSUARIOS);
    firePropertyChange(LOGGEDUSERS);
    firePropertyChange(CURRENTLOGGEDUSER);
}

public void setLoggedUsers(List<Usuario> loggedUsers) {
    this.loggedUsers = loggedUsers;
    firePropertyChange(LOGGEDUSERS);
}

    public List<Usuario> getLoggedUsers() {
    return loggedUsers;
}

    public void loadLoggedUsersFromList() {
        if (usuarios != null) {
            List<Usuario> logged = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                if (usuario.getLogged()) {
                    logged.add(usuario);
                    System.out.println("MODEL DEBUG: Adding to logged list - " + usuario.getId() + " (logged: " + usuario.getLogged() + ")");
                }
            }
            this.setLoggedUsers(logged);
            System.out.println("MODEL: Loaded " + logged.size() + " logged users from " + usuarios.size() + " total users");
        } else {
            System.out.println("Model: usuarios list is null in loadLoggedUsersFromList");
        }
    }

    public void updateUserLoggedStatus(Usuario usuario, boolean logged) {
        for (Usuario user : usuarios) {
            if (user.getId().equals(usuario.getId())) {
                user.setLogged(logged);
                break;
            }
        }
        // Force refresh of logged users list
        loadLoggedUsersFromList();
    }

public void setFilter(Usuario filter) {
    this.filter = filter;
    firePropertyChange(FILTER);
}

public Usuario getFilter() {
    return filter;
}

public int getMode() {
    return mode;
}

public void setMode(int mode) {
    this.mode = mode;
}

public void setCurrent(Usuario current) {
    this.current = current;
    firePropertyChange(CURRENT);
}

public Usuario getCurrent() {
    return current;
}

public List<Usuario> getUsuarios() {
    return usuarios;
}

public void setUsuarios(List<Usuario> usuarios) {
    this.usuarios = usuarios;
    firePropertyChange(LISTAUSUARIOS);
}

public void addUsuario(Usuario usuario) {
    this.usuarios.add(usuario);
    firePropertyChange(LISTAUSUARIOS);
}

public void setCurrentLoggedUser(Usuario currentLoggedUser) {
    this.currentLoggedUser = currentLoggedUser;
}

public Usuario getCurrentLoggedUser() {
    return currentLoggedUser;
}

}