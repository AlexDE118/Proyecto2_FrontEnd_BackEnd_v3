package hospital.presentacion.doctor.actualizar;

import hospital.logic.Doctor;
import hospital.presentacion.doctor.Controller;
import hospital.presentacion.doctor.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JDialog implements PropertyChangeListener {
    private JPanel JPanel_actualizarView;
    private JLabel currentID;
    private JButton cancelarButton;
    private JButton actualizarButton;
    private JTextField textField_actualizarNombre;
    private JTextField textField_actualizarEspecialidad;
    private JTextField textField_actualizarClave;

    public View() {
        setContentPane(JPanel_actualizarView);
        setModal(true);
        getRootPane().setDefaultButton(actualizarButton);
        setLocationRelativeTo(null);
        setTitle("Actualizar");
        setSize(400,250);

        updateFields();

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = model.getCurrent().getId();

                    model.getCurrent().setNombre(textField_actualizarNombre.getText());
                    model.getCurrent().setEspecialidad(textField_actualizarEspecialidad.getText());
                    model.getCurrent().setClave(textField_actualizarClave.getText());

                    controller.updateDoctor(model.getCurrent());
                    controller.loadDoctors();

//                    System.out.println(model.getCurrent().getId());
//                    System.out.println(model.getCurrent().getNombre());
//                    System.out.println(model.getCurrent().getEspecialidad());
//                    System.out.println(model.getCurrent().getClave());

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al realizar el actualizar");
                    System.out.println(ex.getMessage());
                    System.out.println(model.getCurrent());
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

    // --- MVC ---//
    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
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

    private void updateFields() {
        if (model != null && model.getCurrent() != null) {
            Doctor current = model.getCurrent();
            currentID.setText(current.getId() != null ? current.getId() : "");
            textField_actualizarNombre.setText(current.getNombre() != null ? current.getNombre() : "");
            textField_actualizarEspecialidad.setText(current.getEspecialidad() != null ? current.getEspecialidad() : "");
            textField_actualizarClave.setText(current.getClave() != null ? current.getClave() : "");
        } else {
            currentID.setText("Doctor no seleccionado");
            textField_actualizarNombre.setText("");
            textField_actualizarEspecialidad.setText("");
            textField_actualizarClave.setText("");
        }
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            updateFields();
        }
        super.setVisible(visible);
    }

}
