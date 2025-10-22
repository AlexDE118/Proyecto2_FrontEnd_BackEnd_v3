package hospital.presentacion.prescripcion.medicamentos;

import com.github.lgooddatepicker.components.DatePicker;
import hospital.logic.Medicamento;
import hospital.logic.Receta;
import hospital.presentacion.prescripcion.Controller;
import hospital.presentacion.prescripcion.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JDialog implements PropertyChangeListener {
    private JPanel medicamentosJPanel;
    private JComboBox comboBoxSearchMode;
    private JTextField buscar_textField;
    private JButton OKButton;
    private JButton cancelButton;
    private JButton buscarButton;
    private JTable medicamentosTable;
    private JTextField duracionDias_textField;
    private JTextField indicaciones_textField;
    private JTextField cantidad_textField;
    private DatePicker fechaRetiro;

    public View() {
        setContentPane(medicamentosJPanel);
        setModal(true);
        getRootPane().setDefaultButton(OKButton);
        setLocationRelativeTo(null);
        setTitle("Medicamentos");
        setSize(800,400);

        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String modoBusqueda = comboBoxSearchMode.getSelectedItem().toString();
                if(modoBusqueda.equals("Nombre")){
                    System.out.println("Busqueda Nombre");
                    controller.searchMedicamentoNombre(buscar_textField.getText());
                } else if(modoBusqueda.equals("ID")){
                    System.out.println("Busqueda ID");
                    controller.searchMedicamentoID(buscar_textField.getText());
                }

            }
        });

        OKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(medicamentosTable.getSelectedRow() >= 0){
                    //
                    Receta receta = new Receta();
                    try{
                        receta.setMedicamentos(model.getMedicamentoCurrent());
                        //System.out.println(model.getMedicamentoCurrent());
                        receta.setCantidad(Integer.parseInt(cantidad_textField.getText()));
                        receta.setDuracion(Integer.parseInt(duracionDias_textField.getText()));
                        receta.setIndicaciones(indicaciones_textField.getText());
                        model.getRecetas().add(receta);
                        model.setRecetas(model.getRecetas());
                        model.getCurrent().setReceta(model.getRecetas());
                        JOptionPane.showMessageDialog(null, "Medicamentos creado con exito");

                        View.this.setVisible(false);

                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Error al agregar medicina: " + ex.getMessage());
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                View.this.setVisible(false);
            }
        });

        try{
            medicamentosTable.getSelectionModel().addListSelectionListener(e ->{
                if(!e.getValueIsAdjusting()){
                    int row = medicamentosTable.getSelectedRow();
                    if(row != -1){
                        Medicamento medicamento = model.getMedicamentos().get(row);
                        model.setMedicamentoCurrent(medicamento);
                    }
                }
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(medicamentosJPanel, "Error al agregar medicina: " + ex.getMessage());
        }

        //END OF VIEW
    }

    // ----- MVC ----- //
    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.MEDICAMENTOS:
                System.out.println("Medicamentos updated: " + model.getMedicamentos().size());
                int[] cols = {TableModel.ID, TableModel.NAME, TableModel.PRESENTACION};
                medicamentosTable.setModel(new TableModel(cols,model.getMedicamentos()));
                break;
        }
    }
}
