package hospital.presentacion.dashboard;

import com.github.lgooddatepicker.components.DatePicker;
import hospital.logic.Prescripcion;
import hospital.logic.Service;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.util.List;

public class View2 implements java.beans.PropertyChangeListener {
    private JPanel dashboard_JPanel;
    private JComboBox<String> medicamentos_comboBox;
    private JButton mostrarButton;
    private JTable table1;
    private DatePicker fechaDesde;
    private DatePicker fechaHasta;
    private JPanel medicamentos_JPanel;
    private JPanel recetas_JPanel;

    private Controller controller;
    private Model model;

    public JPanel getDashboard_JPanel() {
        return dashboard_JPanel;
    }

    public View2() {
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate desde = fechaDesde.getDate();
                LocalDate hasta = fechaHasta.getDate();
                String medicamento = (String) medicamentos_comboBox.getSelectedItem();

                try {
                    controller.cargarPrescripciones(desde, hasta, medicamento);
                    // ya no mostramos JOptionPane, la tabla se actualizará automáticamente
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dashboard_JPanel,
                            "Error cargando prescripciones: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);

        // llenar combobox con los medicamentos disponibles + "Todos"
        Service service = Service.instance();

        service.loadListaMedicamentos().forEach(m -> {
            String nombre = m.getNombre();
            if (((DefaultComboBoxModel<String>) medicamentos_comboBox.getModel()).getIndexOf(nombre) == -1) {
                medicamentos_comboBox.addItem(nombre);
            }
        });

    }

    private DefaultCategoryDataset buildDataset(List<Prescripcion> prescripciones, String medicamentoSeleccionado) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (prescripciones == null || prescripciones.isEmpty()) return dataset;

        TableModel tableModel = new TableModel(prescripciones);
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            String medicamento = (String) tableModel.getValueAt(row, 0);
            if (medicamento.equalsIgnoreCase(medicamentoSeleccionado)) {
                for (int col = 1; col < tableModel.getColumnCount(); col++) {
                    String mes = tableModel.getColumnName(col);
                    Number cantidad = (Number) tableModel.getValueAt(row, col);
                    dataset.addValue(cantidad, medicamento, mes);
                }
            }
        }
        return dataset;
    }


    private void updateChart(List<Prescripcion> prescripciones, String medicamentoSeleccionado) {
        DefaultCategoryDataset dataset = buildDataset(prescripciones, medicamentoSeleccionado);

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Consumo de " + medicamentoSeleccionado,
                "Mes",
                "Cantidad",
                dataset
        );

        ChartPanel newChartPanel = new ChartPanel(lineChart);

        medicamentos_JPanel.removeAll();
        medicamentos_JPanel.add(newChartPanel);
        medicamentos_JPanel.revalidate();
        medicamentos_JPanel.repaint();
    }

    private DefaultPieDataset buildPieDataset(List<Prescripcion> prescripciones) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        if (prescripciones == null || prescripciones.isEmpty()) return dataset;

        // contar estados
        prescripciones.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Prescripcion::getEstado,
                        java.util.stream.Collectors.counting()
                ))
                .forEach(dataset::setValue);

        return dataset;
    }


    private void updatePieChart(List<Prescripcion> prescripciones) {
        DefaultPieDataset dataset = buildPieDataset(prescripciones);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Estados de Prescripciones",
                dataset,
                true,   // leyenda
                true,   // tooltips
                false   // urls
        );

        ChartPanel newChartPanel = new ChartPanel(pieChart);

        recetas_JPanel.removeAll();
        recetas_JPanel.setLayout(new java.awt.BorderLayout());
        recetas_JPanel.add(newChartPanel, java.awt.BorderLayout.CENTER);
        recetas_JPanel.revalidate();
        recetas_JPanel.repaint();
    }








    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LISTAPRESCRIPCIONES -> {
                // Obtener lista de prescripciones actualizada
                List<Prescripcion> prescripciones = model.getListaPrescripcion();

                // Actualizar tabla
                TableModel tableModel = new TableModel(prescripciones);
                table1.setModel(tableModel);
                table1.updateUI();

                // Ver qué medicamento está seleccionado en el combo
                String medicamentoSeleccionado = (String) medicamentos_comboBox.getSelectedItem();

                // Actualizar gráfico si hay un medicamento seleccionado
                if (medicamentoSeleccionado != null && !medicamentoSeleccionado.isEmpty()) {
                    updateChart(prescripciones, medicamentoSeleccionado);
                }

                updatePieChart(prescripciones);
            }
//            aaa



        }
    }



}

