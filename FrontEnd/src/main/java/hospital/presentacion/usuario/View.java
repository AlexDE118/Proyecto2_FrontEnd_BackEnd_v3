package hospital.presentacion.usuario;

import hospital.logic.Receta;
import hospital.logic.Usuario;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {

    private JPanel JPanel_usuario;
    private JLabel usuarioSeleccionado;
    private JButton enviarMensajeButton;
    private JButton recibirMensajeButton;
    private JTable usuariosOnline;

    public JPanel getJPanel_usuario() {
        return JPanel_usuario;
    }

    public JTable getUsuariosOnline() {
        return usuariosOnline;
    }

    public View(){
        enviarMensajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        recibirMensajeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        usuariosOnline.getSelectionModel().addListSelectionListener(e ->{
            if(!e.getValueIsAdjusting()){
                int row = usuariosOnline.getSelectedRow();
                if(row != -1){
                    Usuario selected = model.getUsuarios().get(row);
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
    }

    public void setModel(Model model){
        this.model = model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Model.CURRENT:
                if(model.getCurrent().getId() != ""){
                    usuarioSeleccionado.setText(model.getCurrent().getId());
                } else usuarioSeleccionado.setText("Usuario aun no seleccionado");
                break;
            case Model.LOGGEDUSERS:
                int[] cols = {TableModel.ID,TableModel.MENSAJES};
                TableModel tableModel = new TableModel(cols,model.getLoggedUsers());
                usuariosOnline.setModel(tableModel);
                usuariosOnline.updateUI();
                break;
        }
    }

}
