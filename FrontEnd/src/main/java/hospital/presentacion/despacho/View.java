package hospital.presentacion.despacho;

import hospital.logic.Prescripcion;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class View extends Component implements PropertyChangeListener{
    private JTextField buscar_textField;
    private JButton buscarPrescripciónButton;
    private JComboBox buscar_comboBox;
    private JTable table1;
    private JComboBox estado_comboBox;
    private JButton guardarButton;
    private JScrollPane prescripciones_JTable;
    private JPanel despacho_JPanel;


  public JPanel getDespacho_JPanel() {
    return despacho_JPanel;
  }

    private Model model;
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;

        // Listener del botón Buscar
        buscarPrescripciónButton.addActionListener(e -> {
            String criterio = buscar_textField.getText();
            controller.buscarPrescripciones(criterio);
        });

        // Listener del botón Guardar estado
        guardarButton.addActionListener(e -> {
            int fila = table1.getSelectedRow();
            if (fila != -1 && model.getPrescripciones().size() > fila) {
                Prescripcion seleccionada = model.getPrescripciones().get(fila);
                String nuevoEstado = (String) estado_comboBox.getSelectedItem();
                controller.actualizarEstado(seleccionada, nuevoEstado);
                JOptionPane.showMessageDialog(null, "Prescripción procesada con exito");

            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una prescripción primero");
            }
        });

        // Listener de selección de tabla
        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = table1.getSelectedRow();
                if (fila != -1 && model.getPrescripciones().size() > fila) {
                    model.setSeleccionada(model.getPrescripciones().get(fila));
                }
            }
        });
    }

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        actualizarTabla(model.getPrescripciones());
    }

    private void actualizarTabla(List<Prescripcion> prescripciones) {
        TableModel tableModel = new TableModel(prescripciones);
        table1.setModel(tableModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Model.PRESCRIPCIONES)) {
            actualizarTabla((List<Prescripcion>) evt.getNewValue());
        }
        if (evt.getPropertyName().equals(Model.SELECCIONADA)) {
            Prescripcion seleccionada = (Prescripcion) evt.getNewValue();
            if (seleccionada != null) {
                estado_comboBox.setSelectedItem(seleccionada.getEstado());
            }
        }
    }


}

