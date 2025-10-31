package hospital.presentacion.medicamentos;

import hospital.logic.Medicamento;
import hospital.presentacion.medicamentos.actualizar.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View3 implements PropertyChangeListener{
    private JPanel medicamentos_JPanel;
    private JTextField nombre_textField;
    private JTextField codigo_textField;
    private JTextField descripcion_textField;
    private JButton verListaButton;
    private JButton guardarButton;
    private JButton buscarButton2;
    private JButton limpiarButton;
    private JTable table_medicamentos;
    private JButton borrarButton;
    private JTextField textField1;
    private JTextField codigo_buscar_textField;
    private JButton actualizarButton;

    private hospital.presentacion.medicamentos.actualizar.View actualizarView;

    public JPanel getMedicamentos_JPanel() {
        return medicamentos_JPanel;
    }

    public View3() {
        actualizarView = new hospital.presentacion.medicamentos.actualizar.View();
        actualizarView.setController(this.controller);
        actualizarView.setModel(this.model);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medicamento medicamento = new Medicamento();
                medicamento.setCodigo(codigo_textField.getText());
                medicamento.setNombre(nombre_textField.getText());
                medicamento.setPresentacion(descripcion_textField.getText());
//
                try{
                    controller.createMedicamentos(medicamento);
                    JOptionPane.showMessageDialog(null, "Medicamento creado con exito");
                    controller.loadMedicamentos();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarView.setVisible(true);
            }
        });

        verListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadMedicamentos();
            }
        });

        buscarButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.searchMedicamento(codigo_buscar_textField.getText());
                codigo_textField.setText("");
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medicamento medicamento = new Medicamento();
                medicamento.setCodigo(codigo_textField.getText());
                try{
                    controller.deleteMedicamento(medicamento);
                    controller.loadMedicamentos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(medicamentos_JPanel, "Error al borrar el Doctor " + ex.getMessage());
                }

            }
        });

        table_medicamentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table_medicamentos.getSelectedRow();
                if (row != -1) {
                    Medicamento selected = model.getMedicamentos().get(row);
                    model.setCurrentMedicamento(selected);
                    System.out.println(selected);
                }
            }
        });

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
//            case Model.CURRENT:
//                ID_textfield.setText(model.getMedico().getId());
//                nombre_textfield.setText(model.getMedico().getNombre());
//                especialidad_textField.setText(model.getMedico().getEspecialidad());
//               textField_clave.setText(model.getMedico().getClave());
//                break;
            case MedicamentosModel.CURRENT:
                if (model.getCurrentMedicamento() != null) {
                    codigo_textField.setText(model.getCurrentMedicamento().getCodigo());
                    nombre_textField.setText(model.getCurrentMedicamento().getNombre());
                    descripcion_textField.setText(model.getCurrentMedicamento().getPresentacion());
                }
                break;
            case MedicamentosModel.LISTAMEDICAMENTOS:
                int[] cols = {MedicamentosTableModel.NOMBRE, MedicamentosTableModel.CODIGO, MedicamentosTableModel.PRESENTACION};
                MedicamentosTableModel tableModel = new MedicamentosTableModel(cols, model.getMedicamentos());
                table_medicamentos.setModel(tableModel);
                table_medicamentos.updateUI();
                break;

        }

    }

    //------- MVC -------//
    MedicamentosController controller;
    MedicamentosModel model;

    public void setController(MedicamentosController controller){
        this.controller = controller;
        if (actualizarView != null) {
            actualizarView.setController(controller);
        }
    }

    public void setModel(MedicamentosModel model){
        this.model = model;
        model.addPropertyChangeListener(this);
        if (actualizarView != null) {
            actualizarView.setModel(model);
        }
    }


}
