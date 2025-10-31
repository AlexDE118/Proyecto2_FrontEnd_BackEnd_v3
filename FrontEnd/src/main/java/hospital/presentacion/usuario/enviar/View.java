package hospital.presentacion.usuario.enviar;

import hospital.presentacion.usuario.Controller;
import hospital.presentacion.usuario.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JDialog implements PropertyChangeListener {
    private JPanel JPanel_enviarMensaje;
    private JLabel currentUser;
    private JTextField textField_message;
    private JButton enviarButton;
    private JButton cancelarButton;

    public View(){
        setContentPane(JPanel_enviarMensaje);
        setModal(true);
        getRootPane().setDefaultButton(enviarButton);
        setLocationRelativeTo(null);
        setTitle("Actualizar");
        setSize(400,250);


        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String message = textField_message.getText();
                    if (message == null || message.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El mensaje no puede estar vacío");
                        return;
                    }
                    model.getCurrent().setMessage(message);
                    if (controller != null) {
                        controller.sendMessageToUser(model.getCurrent());
                        JOptionPane.showMessageDialog(null, "Mensaje enviado exitosamente");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al enviar el Mensaje: " + ex.getMessage());
                }
                setVisible(false);
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
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

    public Model getModel(){
        return model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){

    }
}
