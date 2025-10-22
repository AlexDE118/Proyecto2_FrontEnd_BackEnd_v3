package hospital.presentacion.prescripcion.pacientes;

import hospital.presentacion.prescripcion.Controller;
import hospital.presentacion.prescripcion.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View extends JDialog implements PropertyChangeListener {
    private JPanel contentPanePacientes;
    private JButton OKButton;
    private JButton cancelButton;
    private JTextField nombre_textField;
    private JButton buscarButton;
    private JTable pacientesTable;
    private JComboBox comboBox1;

    public View(){
        setContentPane(contentPanePacientes);
        setModal(true);
        getRootPane().setDefaultButton(OKButton);
        setLocationRelativeTo(null);
        setTitle("Pacientes");
        setSize(400,250);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchMode = comboBox1.getSelectedItem().toString();
                if(searchMode.equals("Nombre")){
                    System.out.println("ComboBox NOMBRE");
                    controller.searchPacientes(nombre_textField.getText());
                } else if(searchMode.equals("ID")){
                    System.out.println("ComboBox ID");
                    controller.searchPacientesid(nombre_textField.getText());
                }
            }
        });

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if(pacientesTable.getSelectedRow()>=0){
                        controller.setPaciente(pacientesTable.getSelectedRow());
                    }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                View.this.setVisible(false);
            }
        });

        //END OF VIEW
    }

    // ---- MVC ---- //

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
            case Model.PACIENTS:
                int[] cols = {TableModel.id,
                        TableModel.NOMBRE, TableModel.TELEFONO };
                pacientesTable.setModel(new TableModel(cols,model.getPacientes()));
                break;
        }
    }
}
