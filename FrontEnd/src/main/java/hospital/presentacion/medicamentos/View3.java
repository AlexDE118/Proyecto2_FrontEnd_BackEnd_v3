package hospital.presentacion.medicamentos;

import hospital.logic.Medicamento;

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

    public JPanel getMedicamentos_JPanel() {
        return medicamentos_JPanel;
    }

    public View3() {
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
    }

    public void setModel(MedicamentosModel model){
        this.model = model;
        model.addPropertyChangeListener(this);
    }


}
