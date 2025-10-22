package hospital.presentacion.historico.consulta;

import hospital.logic.Receta;
import hospital.presentacion.historico.Controller;
import hospital.presentacion.historico.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
public class View extends JDialog implements PropertyChangeListener{
    private JPanel consultaPanel;
    private JButton regresarButton;
    private JTable medicamentosTable;
    private JLabel idPaciente_Label;
    private JLabel nombrePaciente_Label;
    private JLabel fechaConfeccion_JLabel;
    private JLabel fechaRetiro_JLabel;

    public JLabel getIdPaciente_Label() {
        return idPaciente_Label;
    }

    public JLabel getNombrePaciente_Label() {
        return nombrePaciente_Label;
    }

    public JLabel getFechaConfeccion_JLabel() {
        return fechaConfeccion_JLabel;
    }

    public JLabel getFechaRetiro_JLabel() {
        return fechaRetiro_JLabel;
    }

    public View(){
        setContentPane(consultaPanel);
        setModal(true);
        getRootPane().setDefaultButton(regresarButton);
        setLocationRelativeTo(null);
        setTitle("Consulta: ");
        setSize(400,250);
//        idPaciente_Label.setText(model.getCurrent().getPaciente().getId());
//        nombrePaciente_Label.setText(model.getCurrent().getPaciente().getNombre());
//        fechaConfeccion_JLabel.setText(model.getCurrent().getFechaConfeccion().toString());
//        fechaRetiro_JLabel.setText(model.getCurrent().getFechaRetiro().toString());

        //System.out.println(model.getCurrent().getReceta());

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                View.this.setVisible(false);
            }
        });
    }

    // ---- MVC ---- //
    Controller controller;
    Model model;

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void setModel(Model model){
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Model.RECENTAS:
                int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.Cantidad, TableModel.DURACION};
                List<Receta> rows = model.getCurrent().getReceta();
                medicamentosTable.setModel(new TableModel(cols, rows));
                break;
        }

    }
}
