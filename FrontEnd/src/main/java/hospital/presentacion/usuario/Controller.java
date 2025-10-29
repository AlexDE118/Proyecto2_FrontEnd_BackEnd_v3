package hospital.presentacion.usuario;

import hospital.Application;
import hospital.logic.Service;
import hospital.logic.Usuario;
import hospital.presentacion.ThreadListener;

import java.util.List;

public class Controller implements ThreadListener {

    View view;
    Model model;

    SocketListener socketListener;

    public Controller(View view, Model model) {
        model.init();
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        model.addPropertyChangeListener(view);

        try{
            socketListener = new SocketListener(this, (Service.instance().getSid()));
            socketListener.start();
        } catch (Exception e) { }

        getLoggedUsers();
    }

    @Override
    public void deliver_message(String message) {
        try{ search(new Usuario());} catch (Exception e) { }
        System.out.println(message);
    }

    @Override
    public void deliver_login(Usuario usuario) {
        try{
            System.out.println("User logged in notification received: " + usuario.getId());
            // Add to logged users list
            model.addLoggedUser(usuario);

            // Also add to general usuarios list if not already there
            if (!model.getUsuarios().contains(usuario)) {
                model.addUsuario(usuario);
            }

            System.out.println("Logged users count: " + model.getLoggedUsers().size());

        } catch (Exception e) {
            System.out.println("Error adding logged user: " + e.getMessage());
        }
    }

    @Override
    public void deliver_logout(Usuario usuario) {
        try{
            System.out.println("User logged out notification received: " + usuario.getId());
            // Remove from logged users list
            model.removeLoggedUser(usuario);

            // Also remove from general usuarios list
            List<Usuario> currentUsers = model.getUsuarios();
            currentUsers.removeIf(user -> user.getId().equals(usuario.getId()));
            model.setUsuarios(currentUsers);

            System.out.println("Logged users count after logout: " + model.getLoggedUsers().size());

        } catch (Exception e) {
            System.out.println("ERROR al desconectar usuario: " + usuario.getId());
        }
    }

    public void search(Usuario usuario) throws Exception {
        model.setFilter(usuario);
        List<Usuario> rows = Service.instance().searchUsers(model.getFilter());
        model.setMode(Application.MODE_CREATE);
        model.setUsuarios(rows);
    }

    public void loadUsuarios(){
        model.setUsuarios(Service.instance().loadListaUsuarios());
    }

    public void addUsuario(Usuario usuario) {
        Service.instance().addUsuario(usuario);
    }

    public void deleteUsuario(Usuario usuario) {
        Service.instance().removeUsuario(usuario);
    }

    public List<Usuario> getLoggedUsers() {
        return model.getLoggedUsers();
    }

}
