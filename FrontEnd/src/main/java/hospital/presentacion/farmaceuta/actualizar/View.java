package hospital.presentacion.farmaceuta.actualizar;

import hospital.logic.Farmaceuta;
import hospital.presentacion.farmaceuta.Model;
import hospital.presentacion.farmaceuta.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JDialog implements PropertyChangeListener {
    private JLabel currentID;
    private JTextField textField_actualizarNombre;
    private JButton cancelarButton;
    private JButton actualizarButton;
    private JPanel JPanel_actualizarFarmaceutas;
    private JTextField textField_actualizarClave;

    public View(){
        setContentPane(JPanel_actualizarFarmaceutas);
        setModal(true);
        getRootPane().setDefaultButton(actualizarButton);
        setLocationRelativeTo(null);
        setTitle("Actualizar");
        setSize(400,250);

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    model.getCurrent().setNombre(textField_actualizarNombre.getText());
                    controller.updateFarmaceuta(model.getCurrent());
                    controller.loadFarmaceutas();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Error al realizar el actualizar");
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
            Farmaceuta current = model.getCurrent();
            currentID.setText(current.getId() != null ? current.getId() : "");
            textField_actualizarNombre.setText(current.getNombre() != null ? current.getNombre() : "");
            textField_actualizarClave.setText(current.getClave() != null ? current.getClave() : "");
        } else {
            currentID.setText("Farmaceuta no seleccionado");
            textField_actualizarNombre.setText("");
            textField_actualizarClave.setText("");
        }
    }
}
