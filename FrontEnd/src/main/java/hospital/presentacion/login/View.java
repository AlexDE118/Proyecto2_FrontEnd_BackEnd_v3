package hospital.presentacion.login;

import hospital.logic.Service;
import hospital.logic.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel loginJPanel;
    private JPasswordField passwordField1;
    private JTextField id_textField;
    private JButton cambiarClaveButton;
    private JButton logInButton;
    private JButton limpiarButton;

    public JPanel getLoginJPanel() {
        return loginJPanel;
    }

    Controller controller;
    Model model;

    public View() {


        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario = new Usuario();
                usuario.setId(id_textField.getText());
                usuario.setClave(passwordField1.getText());
                try {
                    // This now properly calls the backend login which updates logged status
                    Usuario loggedUser = controller.login(usuario);
                    model.setCurrent(loggedUser);

                    // Optional: Show success message
                    //JOptionPane.showMessageDialog(loginJPanel, "Login exitoso para: " + loggedUser.getId());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(loginJPanel, "Error: " + ex.getMessage());
                }
            }
//            public void actionPerformed(ActionEvent e) {
//                Usuario usuario = new Usuario();
//                usuario.setId(id_textField.getText());
//                usuario.setClave(passwordField1.getText());
//                try {
//                    Usuario loggedUser = controller.login(usuario);
//                    model.setCurrent(loggedUser);
//
////                    Service.instance().readUsuario(usuario);
////                    model.setCurrent(usuario);
//
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(loginJPanel, "Error: " + ex.getMessage());
//                }
//            }
        });


        cambiarClaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = id_textField.getText();
                if (userId.isEmpty()) {
                    JOptionPane.showMessageDialog(loginJPanel, "Debe ingresar un ID de usuario primero");
                    return;
                }

                String nuevaClave = JOptionPane.showInputDialog(
                        loginJPanel,
                        "Ingrese la nueva clave para el usuario " + userId + ":",
                        "Cambiar Clave",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (nuevaClave != null && !nuevaClave.trim().isEmpty()) {
                    try {
                        Usuario u = new Usuario();
                        u.setId(userId);
                        u.setClave(nuevaClave);


                        Service.instance().updateClave(u);

                        JOptionPane.showMessageDialog(loginJPanel, "Clave actualizada con éxito");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(loginJPanel, "Error al cambiar clave: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(loginJPanel, "La clave no puede estar vacía");
                }
            }
        });


        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id_textField.setText("");
                passwordField1.setText("");
                JOptionPane.showMessageDialog(loginJPanel, "Campos limpiados");
            }
        });

    }

    public JButton getLogInButton() { return logInButton; }

    public JTextField getIdTextField() { return id_textField; }

    public JTextField getPasswordField1() { return passwordField1; }



    @Override
    public void propertyChange(PropertyChangeEvent evt){
        switch (evt.getPropertyName()){
            case Model.CURRENT:

            break;
            case Model.LISTA_USUARIOS:

            break;
        }
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public String modeSelector(String ID){
        if(id_textField.getText().equals("Admin")){
            return "Admin";
        }
        return "INVALID";
    }


}
