package hospital.presentacion.usuario;

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
        List<Usuario> usuarios = Service.instance().loadListaUsuarios();
        model.setUsuarios(usuarios);
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

    @Override
    public void deliver_login(Usuario usuario){
        try {
            // Update the user's logged status in the model
            List<Usuario> allUsers = model.getUsuarios();
            for (Usuario user : allUsers) {
                if (user.getId().equals(usuario.getId())) {
                    user.setLogged(true);
                    break;
                }
            }

            // Refresh the logged users list
            model.loadLoggedUsersFromList();

            System.out.println("Usuario " + usuario.getId() + " ha iniciado sesión");

        } catch (Exception e) {
            System.err.println("Error en deliver_login: " + e.getMessage());
        }
    }

    @Override
    public void deliver_logout(Usuario usuario){
        try {
            // Update the user's logged status in the model
            List<Usuario> allUsers = model.getUsuarios();
            for (Usuario user : allUsers) {
                if (user.getId().equals(usuario.getId())) {
                    user.setLogged(false);
                    break;
                }
            }

            // Refresh the logged users list
            model.loadLoggedUsersFromList();

            System.out.println("Usuario " + usuario.getId() + " ha cerrado sesión");

        } catch (Exception e) {
            System.err.println("Error en deliver_logout: " + e.getMessage());
        }
    }

    public List<Usuario> loadListaUsuariosLogeados(){
        return model.getUsuarios();
    }


}
