package hospital.presentacion.usuario;

import hospital.logic.Service;
import hospital.logic.Usuario;
import hospital.presentacion.Sesion;
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

        // Load all users from service
        List<Usuario> usuarios = Service.instance().loadListaUsuarios();
        model.setUsuarios(usuarios);

        // IMPORTANT: Check if current session user is in the list and mark as logged
        Usuario currentSessionUser = Sesion.getUsuario();
        if (currentSessionUser != null) {
            for (Usuario usuario : usuarios) {
                if (usuario.getId().equals(currentSessionUser.getId())) {
                    usuario.setLogged(true);
                    System.out.println("Controller: Marked current session user " + usuario.getId() + " as logged");
                    break;
                }
            }
        }

        model.loadLoggedUsersFromList();

        try{
            socketListener = new SocketListener(this, (Service.instance().getSid()));
            socketListener.start();
            System.out.println("Usuario - Controller Servidor Iniciado");
        } catch (Exception e) {
            System.err.println("Usuario Controller - Error al iniciar el servidor "+ e.getMessage());
        }
    }

    @Override
    public void deliver_message(String message){

    }

//    @Override
//    public void deliver_login(Usuario usuario){
//        try {
//            // Use the new method to update status and refresh
//            model.updateUserLoggedStatus(usuario, true);
//            System.out.println("Usuario " + usuario.getId() + " ha iniciado sesión");
//        } catch (Exception e) {
//            System.err.println("Error en deliver_login: " + e.getMessage());
//        }
//    }

    @Override
    public void deliver_login(Usuario usuario){
        try {
            System.out.println("Controller: Received login notification for " + usuario.getId());

            // Reload the complete user list to ensure we have current data
            List<Usuario> allUsers = Service.instance().loadListaUsuarios();

            boolean found = false;
            for (Usuario user : allUsers) {
                if (user.getId().equals(usuario.getId())) {
                    user.setLogged(true);
                    found = true;
                    System.out.println("Controller: Updated user " + usuario.getId() + " to logged=true");
                    break;
                }
            }

            if (!found) {
                // If user not found, add them with logged status
                usuario.setLogged(true);
                allUsers.add(usuario);
                System.out.println("Controller: Added new user " + usuario.getId() + " with logged=true");
            }

            // Update the model with the refreshed list
            model.setUsuarios(allUsers);
            model.loadLoggedUsersFromList();

            System.out.println("Controller: Logged users count: " + model.getLoggedUsers().size());

        } catch (Exception e) {
            System.err.println("Error en deliver_login: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    @Override
//    public void deliver_logout(Usuario usuario){
//        try {
//            // Use the new method to update status and refresh
//            model.updateUserLoggedStatus(usuario, false);
//            System.out.println("Usuario " + usuario.getId() + " ha cerrado sesión");
//        } catch (Exception e) {
//            System.err.println("Error en deliver_logout: " + e.getMessage());
//        }
//    }

    @Override
    public void deliver_logout(Usuario usuario){
        try {
            System.out.println("Controller: Received logout notification for " + usuario.getId());

            // Update the user's logged status in the model
            List<Usuario> allUsers = model.getUsuarios();
            for (Usuario user : allUsers) {
                if (user.getId().equals(usuario.getId())) {
                    user.setLogged(false);
                    System.out.println("Controller: Updated user " + usuario.getId() + " to logged=false");
                    break;
                }
            }

            // Refresh the logged users list
            model.loadLoggedUsersFromList();
            System.out.println("Controller: Logged users count after logout: " + model.getLoggedUsers().size());

        } catch (Exception e) {
            System.err.println("Error en deliver_logout: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Usuario> loadListaUsuariosLogeados(){
        return model.getUsuarios();
    }


}
