package hospital.presentacion.historico;

import hospital.logic.Prescripcion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel historicoJPanel;
    private JTable prescripcionesTable;
    private JButton consultarDetallesButton;
    private JButton refrescarButton;

    private hospital.presentacion.historico.consulta.View consultaView;



    public JPanel getHistoricoJPanel() {
        return historicoJPanel;
    }

    public View(){
        consultaView = new hospital.presentacion.historico.consulta.View();
        consultarDetallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultaView.setVisible(true);
            }
        });

        refrescarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadPrescripciones();
                System.out.println(model.getPrescripciones());
            }
        });

        prescripcionesTable.getSelectionModel().addListSelectionListener(x ->{
            if(!x.getValueIsAdjusting()){
                int row =  prescripcionesTable.getSelectedRow();
                if(row != -1){
                    Prescripcion selected = model.getPrescripciones().get(row);
                    model.setCurrent(selected);
                    consultaView.getIdPaciente_Label().setText(selected.getPaciente().getId());
                    consultaView.getNombrePaciente_Label().setText(selected.getPaciente().getNombre());
                    consultaView.getFechaConfeccion_JLabel().setText(selected.getFechaConfeccion().toString());
                    consultaView.getFechaRetiro_JLabel().setText(selected.getFechaRetiro().toString());
                }
            }
        });
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

        consultaView.setModel(model);
        model.addPropertyChangeListener(consultaView);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CURRENT:
            // N/A aaa
            break;
            case Model.PRESCRIPCIONES:
            int[] cols = {TableModel.PACIENTE_ID,TableModel.PACIENTE_NOMBRE, TableModel.ESTADO};
            prescripcionesTable.setModel(new TableModel(cols,model.getPrescripciones()));
            break;
        }
    }
}
