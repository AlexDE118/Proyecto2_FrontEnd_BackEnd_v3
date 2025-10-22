package hospital.presentacion.paciente;

import hospital.logic.Paciente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel pacientesJPanel;
    private JTextField id_textField;
    private JTextField nombre_textField;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JTextField nombreBuscar_textField;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable listaPacientes_Table;
    private JTextField telefono_textField;

    public JPanel getPacientesJPanel() {
        telefono_textField.setText("");
        return pacientesJPanel;
    }

    public View(){


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Paciente p =  new Paciente();
                p.setNombre(nombre_textField.getText());
                p.setId(id_textField.getText());
                p.setNumeroTelefono(telefono_textField.getText());
                try{
                    controller.createPaciente(p);
                    JOptionPane.showMessageDialog(null, "Paciente guardado exitosamente");
                    controller.loadPacientes();
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(pacientesJPanel, "Error al crear paciente: " + ex.getMessage());
                }
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telefono_textField.setText("");
                id_textField.setText("");
                nombre_textField.setText("");
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.searchPaciente(nombreBuscar_textField.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(pacientesJPanel, "Error al buscar paciente: " + ex.getMessage());
                }
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Paciente p =  new Paciente();
                p.setId(id_textField.getText());
                try{
                    controller.deletePaciente(p);
                    controller.loadPacientes();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(pacientesJPanel, "Error al borrar paciente: " + ex.getMessage());
                }
            }
        });

        reporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        try {
            listaPacientes_Table.getSelectionModel().addListSelectionListener( e->{
                if (!e.getValueIsAdjusting()) {
                    int row = listaPacientes_Table.getSelectedRow();
                    if(row !=-1){
                        Paciente selected = model.getListaPacientes().get(row);
                        model.setCurrent(selected);
                    }
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pacientesJPanel, "Error al seleccionar paciente: " + e.getMessage());
        }

//        listaPacientes_Table.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int row = listaPacientes_Table.getSelectedRow();
//                if(row != -1){
//                    Paciente selected = model.getListaPacientes().get(row);
//                    model.setCurrent(selected);
//                }
//            }
//        });
    }


    Model model;
    Controller controller;

    public void setModel(Model model){
        this.model=model;
    }
    public void setController(Controller controller){
        this.controller=controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Model.CURRENT:
                id_textField.setText(model.getCurrent().getId());
                nombre_textField.setText(model.getCurrent().getNombre());
                telefono_textField.setText(model.getCurrent().getNumeroTelefono());
                break;
            case Model.LISTAPACIENTES:
                int[] cols = {TableModel.id, TableModel.NOMBRE, TableModel.TELEFONO};
                TableModel tableModel = new TableModel(cols,model.getListaPacientes());
                listaPacientes_Table.setModel(tableModel);
                listaPacientes_Table.updateUI();
                break;
        }

    }
}
