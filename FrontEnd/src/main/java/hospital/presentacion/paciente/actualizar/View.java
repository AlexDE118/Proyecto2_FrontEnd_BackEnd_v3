package hospital.presentacion.paciente.actualizar;

import hospital.presentacion.paciente.Model;
import hospital.presentacion.paciente.Controller;
import hospital.logic.Paciente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JDialog implements PropertyChangeListener {
    private JPanel JPanel_atualizarPacientes;
    private JLabel currentID;
    private JTextField textField_actualizarNombre;
    private JTextField textField_actualizarTelefono;
    private JButton cancelarButton;
    private JButton actualizarButton;


    public View(){
        setContentPane(JPanel_atualizarPacientes);
        setModal(true);
        getRootPane().setDefaultButton(actualizarButton);
        setLocationRelativeTo(null);
        setTitle("Actualizar");
        setSize(400,250);

        updateFields();

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    model.getCurrent().setNombre(textField_actualizarNombre.getText());
                    model.getCurrent().setNumeroTelefono(textField_actualizarTelefono.getText());
                    controller.updatePaciente(model.getCurrent());
                    controller.loadPacientes();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Error al actualizar paciente" + ex.getMessage());
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
        if (this.model != null) {
            this.model.addPropertyChangeListener(this);
        }
        updateFields();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CURRENT:
                updateFields();
                break;
        }
    }

    private void updateFields(){
        if(model != null && model.getCurrent() != null){
            Paciente current = model.getCurrent();
            currentID.setText(current.getId() != null ? current.getId() : "");
            textField_actualizarNombre.setText(current.getNombre() != null ? current.getNombre() : "");
            textField_actualizarTelefono.setText(current.getNumeroTelefono() != null ? current.getNumeroTelefono() : "");
        } else{
            currentID.setText("Paciente no seleccionado");
            textField_actualizarNombre.setText("");
            textField_actualizarTelefono.setText("");
        }
    }

}
