package hospital.presentacion.doctor;

import hospital.logic.Doctor;
import hospital.logic.Usuario;
import hospital.logic.Service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JLabel ID_label;
    private JTextField ID_textfield;
    private JTextField especialidad_textField;
    private JLabel especialidad_label;
    private JTextField nombre_textfield;
    private JLabel nombre_label;
    private JPanel Medicos_JPanel;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JLabel buscar_label;
    private JTextField buscar_textfield;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable listaDoctores_JTable;
    private JScrollPane JScrollPane;

    public JPanel getMedicos_JPanel() {
        return Medicos_JPanel;
    }

    public View() {

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor doctor = new Doctor();
                doctor.setId(ID_textfield.getText());
                doctor.setNombre(nombre_textfield.getText());
                doctor.setEspecialidad(especialidad_textField.getText());
                doctor.setClave(ID_textfield.getText());
                Usuario usuario = new Usuario();
                usuario.setId(ID_textfield.getText());
                usuario.setClave(ID_textfield.getText());
                usuario.setUserType(doctor.getClass().getSimpleName());
                try {
                    if (!validate()) {
                        return;
                    }
                    controller.createDoctor(doctor);
                    Service.instance().addUsuario(usuario);
                    JOptionPane.showMessageDialog(null, "Doctor guardado exitosamente");
                    controller.loadDoctors();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Medicos_JPanel, "Error al crear el Doctor " + ex.getMessage());
                }
            }

        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.searchDoctor(buscar_textfield.getText());
                nombre_textfield.setText("");
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor doctor = new Doctor();
                doctor.setId(ID_textfield.getText());
                try{
                    controller.deleteDoctor(doctor);
                    controller.loadDoctors();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Medicos_JPanel, "Error al borrar el Doctor " + ex.getMessage());
                }

            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ID_textfield.setText("");
                nombre_textfield.setText("");
                especialidad_textField.setText("");
            }
        });

        listaDoctores_JTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = listaDoctores_JTable.getSelectedRow();
                if (row != -1) {
                    Doctor selected = model.getListaDoctores().get(row);
                    model.setCurrent(selected);
                }
            }
        });

    //END OF VIEW//
    }

    //================== MVC ==================//
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
            case Model.CURRENT:
                ID_textfield.setText(model.getCurrent().getId());
                nombre_textfield.setText(model.getCurrent().getNombre());
                especialidad_textField.setText(model.getCurrent().getEspecialidad());
                break;
            case Model.LISTADOCTORES:
                int[] cols = {TableModel.ID,TableModel.NAME, TableModel.ESPECIALIAD};
                TableModel tableModel = new TableModel(cols, model.getListaDoctores());
                listaDoctores_JTable.setModel(tableModel);
                listaDoctores_JTable.updateUI();
                break;
        }
    }

    public boolean validate(){
        if(ID_textfield.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe ingresar un ID");
            return false;
        }
        if(nombre_textfield.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre");
            return false;
        }
        if(especialidad_textField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe ingresar un especialidad");
            return false;
        }
        return true;
    }
}

