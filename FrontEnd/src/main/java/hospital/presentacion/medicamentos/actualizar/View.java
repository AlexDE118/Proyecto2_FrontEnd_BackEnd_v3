package hospital.presentacion.medicamentos.actualizar;

import hospital.logic.Medicamento;
import hospital.presentacion.farmaceuta.Model;
import hospital.presentacion.medicamentos.MedicamentosModel;
import hospital.presentacion.medicamentos.MedicamentosController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JDialog implements PropertyChangeListener {
    private JPanel JPanel_actualizarMedicamentos;
    private JLabel currentID;
    private JTextField textField_actualizarNombre;
    private JTextField textField_actualizarPresentacion;
    private JButton cancelarButton;
    private JButton actualizarButton;

    public View(){
        setContentPane(JPanel_actualizarMedicamentos);
        setModal(true);
        getRootPane().setDefaultButton(actualizarButton);
        setLocationRelativeTo(null);
        setTitle("Actualizar");
        setSize(400,250);

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String nombre = textField_actualizarNombre.getText();
                    String presentacion = textField_actualizarPresentacion.getText();

                    model.getCurrentMedicamento().setNombre(nombre);
                    model.getCurrentMedicamento().setPresentacion(presentacion);
                    controller.updateMedicamentos(model.getCurrentMedicamento());
                    controller.loadMedicamentos();

                    JOptionPane.showMessageDialog(null, "Actualizado exitosamente");

                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Error al realizar el actualizar");
                    System.out.println(model.getCurrentMedicamento());
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

    MedicamentosController controller;
    MedicamentosModel model;

    public void setController(MedicamentosController controller){
        this.controller = controller;
    }

    public void setModel(MedicamentosModel model){
        this.model = model;
        if (this.model != null) {
            this.model.addPropertyChangeListener(this);
        }
        updateFields();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case MedicamentosModel.CURRENT:
                updateFields();
                break;
        }
    }

    private void updateFields() {
        if (model != null && model.getCurrentMedicamento() != null) {
            Medicamento current = model.getCurrentMedicamento();
            currentID.setText(current.getCodigo() != null ? current.getCodigo() : "");
            textField_actualizarNombre.setText(current.getNombre() != null ? current.getNombre() : "");
            textField_actualizarPresentacion.setText(current.getPresentacion() != null ? current.getPresentacion() : "");
        } else {
            currentID.setText("Medicamento no seleccionado");
            textField_actualizarNombre.setText("");
            textField_actualizarPresentacion.setText("");
        }
    }
}
