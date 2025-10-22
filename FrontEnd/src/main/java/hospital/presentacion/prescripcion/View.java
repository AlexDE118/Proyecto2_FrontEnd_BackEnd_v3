package hospital.presentacion.prescripcion;

import com.github.lgooddatepicker.components.DatePicker;
import hospital.logic.Prescripcion;
import hospital.logic.Receta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


public class View implements PropertyChangeListener {
    private JPanel prescripcionJPanel;
    private JButton buscarPacienteButton;
    private JButton agregarMedicamentoButton;
    private JLabel pacienteLabel;
    private JTable listaMedicamentos_JTable;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton descartarMedicamentoButton;
    private DatePicker fechaConfeccion;
    private DatePicker fechaRetiro;
    private JComboBox estado_comboBox;

    private hospital.presentacion.prescripcion.pacientes.View pacienteView;
    private hospital.presentacion.prescripcion.medicamentos.View medicamentosView;

    public JPanel getPrescripcionJPanel() {
        return prescripcionJPanel;
    }

    public View(){
        pacienteView = new hospital.presentacion.prescripcion.pacientes.View();
        medicamentosView = new hospital.presentacion.prescripcion.medicamentos.View();

        agregarMedicamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadMedicamentos();
                medicamentosView.setVisible(true);
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prescripcion prescripcion = new Prescripcion();
                try {
                    prescripcion.setPaciente(model.current.getPaciente());
                    prescripcion.setReceta(model.current.getReceta());

                    // Aquí tomas la fecha del DatePicker
                    if (fechaConfeccion.getDate() != null) {
                        prescripcion.setFechaConfeccion(fechaConfeccion.getDate());
                    } else {
                        JOptionPane.showMessageDialog(prescripcionJPanel,
                                "Debe seleccionar una fecha de confección antes de guardar.");
                        return; // Evita guardar sin fecha
                    }

                    if (fechaRetiro.getDate() != null) {
                        prescripcion.setFechaRetiro(fechaRetiro.getDate());
                    } else {
                        JOptionPane.showMessageDialog(prescripcionJPanel,
                                "Debe seleccionar una fecha de confección antes de guardar.");
                        return; // Evita guardar sin fecha
                    }

                    if (estado_comboBox.getSelectedItem() != null) {
                        prescripcion.setEstado(estado_comboBox.getSelectedItem().toString());
                    } else {
                        JOptionPane.showMessageDialog(prescripcionJPanel,
                                "Debe seleccionar un estado antes de guardar.");
                        return;
                    }

                    controller.createPrescripcion(prescripcion);
                    JOptionPane.showMessageDialog(prescripcionJPanel,
                            "Prescripción guardada exitosamente");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(prescripcionJPanel,
                            "Error al agregar prescripción: " + ex.getMessage());
                }
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRecetas(new ArrayList<Receta>());
                model.setCurrent(new Prescripcion());
                controller.loadRecetas();
            }
        });

        descartarMedicamentoButton.addActionListener(e -> {
            int row = listaMedicamentos_JTable.getSelectedRow();
            if (row != -1) {
                Receta receta = model.getCurrent().getReceta().get(row);
                Prescripcion prescripcion = model.getCurrent(); // la que se está editando

                controller.removeRecetaFromPrescripcion(prescripcion, receta);
            } else {
                JOptionPane.showMessageDialog(prescripcionJPanel, "Seleccione un medicamento para descartar.");
            }
        });


        buscarPacienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pacienteView.setVisible(true);
            }
        });

        listaMedicamentos_JTable.getSelectionModel().addListSelectionListener(e ->{
            if(!e.getValueIsAdjusting()){
                int row = listaMedicamentos_JTable.getSelectedRow();
                if(row != -1){
                    Receta receta = model.getRecetas().get(row);

                    //model.setCurrentReceta(receta);
                }
            }
        });
    }

    // ---- MVC ---- //

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
        pacienteView.setController(controller);
        medicamentosView.setController(controller);
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        pacienteView.setModel(model);
        model.addPropertyChangeListener(this);

        medicamentosView.setModel(model);
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Model.RECETAS:
                int[] cols = {TableModel.MEDICAMENTO,TableModel.CANTIDAD,TableModel.DURACION,TableModel.INDICACIONES};
                listaMedicamentos_JTable.setModel(new TableModel(cols,model.getRecetas()));
                break;
            case Model.PACIENT: {
                if (model.getCurrent().getPaciente() != null) {
                    pacienteLabel.setText(model.getCurrent().getPaciente().getNombre());
                } else pacienteLabel.setText("Paciente no seleccionado");
            }
            break;
            //case : {}
        }
    }
}
