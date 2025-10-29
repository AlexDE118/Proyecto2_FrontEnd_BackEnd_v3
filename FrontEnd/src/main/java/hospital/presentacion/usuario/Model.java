package hospital.presentacion.usuario;

import hospital.logic.Usuario;
import hospital.presentacion.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
Usuario filter;
Usuario current;
List<Usuario> usuarios;
List<Usuario> loggedUsers;
int mode;

public static final String CURRENT = "current";
public static final String LISTAUSUARIOS = "listaUsuarios";
public static final String FILTER = "filter";
public static final String LOGGEDUSERS = "loggedUsers";

public Model() {
    loggedUsers = new ArrayList<>();
}

public void init() {
    filter = new Usuario();
    current = new Usuario();
    List<Usuario> rows= new ArrayList<>();
    this.setUsuarios(rows);
    //mode = Application.MODE_CREATE;
}

@Override
public void addPropertyChangeListener(PropertyChangeListener listener) {
    super.addPropertyChangeListener(listener);
    firePropertyChange(CURRENT);
    firePropertyChange(LISTAUSUARIOS);
    firePropertyChange(LOGGEDUSERS);
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
                }
            }
            this.setLoggedUsers(logged);
            System.out.println("Loaded " + logged.size() + " logged users from " + usuarios.size() + " total users");
        } else {
            System.out.println("usuarios list is null in loadLoggedUsersFromList");
        }
    }

//public void addLoggedUser(Usuario usuario) {
//    if (!isUserLogged(usuario)) {
//        this.loggedUsers.add(usuario);
//        firePropertyChange(LOGGEDUSERS);
//    }
//}
//
//public void removeLoggedUser(Usuario usuario) {
//    boolean removed = this.loggedUsers.removeIf(user -> user.getId().equals(usuario.getId()));
//    if (removed) {
//        firePropertyChange(LOGGEDUSERS);
//    }
//}
//
//public boolean isUserLogged(Usuario usuario) {
//    return this.loggedUsers.stream()
//            .anyMatch(user -> user.getId().equals(usuario.getId()));
//}

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
}