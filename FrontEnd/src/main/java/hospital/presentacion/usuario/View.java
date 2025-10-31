package hospital.presentacion.usuario;

import hospital.logic.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JOptionPane implements PropertyChangeListener {

    private JPanel JPanel_usuarios;
    private JLabel usuarioSeleccionado;
    private JButton enviarButton;
    private JButton refrescarButton;
    private JButton recibirButton;
    private JTable usuariosOnline;
    private JLabel usuarioActual;
    private hospital.presentacion.usuario.enviar.View enviarView; //= new hospital.presentacion.usuario.enviar.View();


    public JPanel getJPanel_usuario() {
        return JPanel_usuarios;
    }
    public JLabel getUsuarioActual(){ return usuarioActual; }
    public JTable getUsuariosOnline() {
        return usuariosOnline;
    }

    public View(){

        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarView.getModel().setCurrent(model.getCurrent());
                enviarView.setVisible(true);
            }
        });

        recibirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = model.getCurrent().getMessage();
                if (message != null && !message.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Mensaje recibido:\n" + message,
                            "Mensaje",
                            JOptionPane.INFORMATION_MESSAGE);
                    model.getCurrent().setMessage("");
                    controller.setMessageToEmpty(model.getCurrent());
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No hay mensajes para el usuario seleccionado",
                            "Sin mensajes",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        refrescarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //model.getLoggedUsers();
                System.out.println(model.getLoggedUsers());
                System.out.println(model.getUsuarios().toString());
                for(Usuario usuario : model.getUsuarios()){
                    System.out.println(usuario.getLogged());
                }
            }
        });

        usuariosOnline.getSelectionModel().addListSelectionListener(e ->{
            if(!e.getValueIsAdjusting()){
                int row = usuariosOnline.getSelectedRow();
                if(row != -1){
                    Usuario selected = model.getLoggedUsers().get(row);
                    model.setCurrent(selected);
                }
            }
        });
    }

    //--- MVC ---//
    Controller controller;
    Model model;

    public void setController(Controller controller){
        this.controller = controller;
        this.enviarView = new hospital.presentacion.usuario.enviar.View();
        this.enviarView.setController(this.controller);
    }

    public void setModel(Model model){
        this.model = model;
        if(this.enviarView != null){
            this.enviarView.setModel(model);
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Model.CURRENT:
                if(model.getCurrent().getId() != null && !model.getCurrent().getId().isEmpty()){
                    usuarioSeleccionado.setText(model.getCurrent().getId());
                    //usuarioActual.setText(model.getCurrent().getId());
                } else {
                    usuarioSeleccionado.setText("Usuario aun no seleccionado");
                }
                break;
            case Model.LOGGEDUSERS:
                int[] cols = {TableModel.ID, TableModel.MENSAJES};
                TableModel tableModel = new TableModel(cols, model.getLoggedUsers());
                usuariosOnline.setModel(tableModel);
                // Optional: Adjust column widths if needed
                //usuariosOnline.getColumnModel().getColumn(TableModel.MENSAJES).setMaxWidth(80);
                usuariosOnline.updateUI();
                break;
        }
    }
}
