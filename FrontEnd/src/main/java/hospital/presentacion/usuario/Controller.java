package hospital.presentacion.usuario;

import hospital.logic.Service;
import hospital.logic.Usuario;
import hospital.presentacion.Sesion;
import hospital.presentacion.ThreadListener;

import javax.swing.*;
import java.util.List;

public class Controller extends JOptionPane implements ThreadListener {
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
        Usuario currentSessionUser = Sesion.getUsuario();
        if (currentSessionUser != null) {
            model.setCurrent(currentSessionUser);
            view.getUsuarioActual().setText(currentSessionUser.getId());

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
    public void deliver_message_status_change(Usuario usuario) {
        // Update the user in the local model
        for (Usuario user : model.getUsuarios()) {
            if (user.getId().equals(usuario.getId())) {
                user.setMessage(usuario.getMessage());
                break;
            }
        }

        // Refresh the table to show the updated message status
        model.loadLoggedUsersFromList();
        fireTableDataChanged();

        System.out.println("Controller: Received message status change for " + usuario.getId());
    }

    public void setMessageToEmpty(Usuario usuario) {
        try {
            Usuario updatedUser = Service.instance().readUsuario(usuario);
            updatedUser.setMessage("");
            Service.instance().sendMessage(updatedUser);
            System.out.println("Message cleared for: " + updatedUser.getId());
            for (Usuario user : model.getUsuarios()) {
                if (user.getId().equals(updatedUser.getId())) {
                    user.setMessage("");
                    break;
                }
            }
            model.getCurrent().setMessage("");
            broadcastMessageStatusChange(updatedUser);
            refreshUsersList();

        } catch (Exception e) {
            System.err.println("Error clearing message: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al limpiar el mensaje: " + e.getMessage());
        }
    }

    private void broadcastMessageStatusChange(Usuario usuario) {
        try {
            Service.instance().broadcastMessageStatusChange(usuario);
        } catch (Exception e) {
            System.err.println("Error broadcasting message status: " + e.getMessage());
        }
    }

//    public void setMessageToEmpty(Usuario usuario) {
//        try {
//            Usuario currentUser = Sesion.getUsuario();
//            String messageContent = model.getCurrent().getMessage();
//            usuario.setMessage("");
//            Service.instance().sendMessage(usuario);
//            System.out.println("Mensaje enviado a: " + usuario.getId());
//            model.getCurrent().setMessage("");
//            refreshUsersList();
//
//        } catch (Exception e) {
//            System.err.println("Error al enviar mensaje: " + e.getMessage());
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error al enviar el mensaje: " + e.getMessage());
//        }
//    }

    public void sendMessageToUser(Usuario recipientUser) {
        try {
            Usuario currentUser = Sesion.getUsuario();
            String messageContent = model.getCurrent().getMessage();

            if (messageContent == null || messageContent.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El mensaje no puede estar vacío");
                return;
            }
            Usuario updatedRecipient = Service.instance().readUsuario(recipientUser);
            String formattedMessage = currentUser.getId() + ": " + messageContent;
            updatedRecipient.setMessage(formattedMessage);
            Service.instance().sendMessage(updatedRecipient);
            System.out.println("Mensaje enviado a: " + updatedRecipient.getId());
            for (Usuario user : model.getUsuarios()) {
                if (user.getId().equals(updatedRecipient.getId())) {
                    user.setMessage(formattedMessage);
                    break;
                }
            }

            model.getCurrent().setMessage("");
            broadcastMessageStatusChange(updatedRecipient);
            refreshUsersList();

        } catch (Exception e) {
            System.err.println("Error al enviar mensaje: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al enviar el mensaje: " + e.getMessage());
        }
    }

    private void refreshUsersList() {
        try {
            List<Usuario> updatedUsers = Service.instance().loadListaUsuarios();
            model.setUsuarios(updatedUsers);
            model.loadLoggedUsersFromList();
            fireTableDataChanged();

        } catch (Exception e) {
            System.err.println("Error refreshing users list: " + e.getMessage());
        }
    }

    private void fireTableDataChanged() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int[] cols = {TableModel.ID, TableModel.MENSAJES};
                TableModel tableModel = new TableModel(cols, model.getLoggedUsers());
                view.getUsuariosOnline().setModel(tableModel);
                view.getUsuariosOnline().updateUI();
            }
        });
    }

    @Override
    public void deliver_message(String message){
        refreshUsersList();
        Usuario currentUser = Sesion.getUsuario();
        if (currentUser != null) {
            for (Usuario user : model.getUsuarios()) {
                if (user.getId().equals(currentUser.getId()) &&
                        user.getMessage() != null && !user.getMessage().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Tienes un nuevo mensaje:\n" + user.getMessage(),
                            "Nuevo Mensaje",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }

    @Override
    public void deliver_login(Usuario usuario){
        try {
            System.out.println("Controller: Received login notification for " + usuario.getId());
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
                usuario.setLogged(true);
                allUsers.add(usuario);
                System.out.println("Controller: Added new user " + usuario.getId() + " with logged=true");
            }
            model.setUsuarios(allUsers);
            model.loadLoggedUsersFromList();

            System.out.println("Controller: Logged users count: " + model.getLoggedUsers().size());

        } catch (Exception e) {
            System.err.println("Error en deliver_login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void deliver_logout(Usuario usuario){
        try {
            System.out.println("Controller: Received logout notification for " + usuario.getId());
            List<Usuario> allUsers = model.getUsuarios();
            for (Usuario user : allUsers) {
                if (user.getId().equals(usuario.getId())) {
                    user.setLogged(false);
                    System.out.println("Controller: Updated user " + usuario.getId() + " to logged=false");
                    break;
                }
            }
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
