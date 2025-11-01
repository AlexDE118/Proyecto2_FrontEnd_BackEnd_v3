package hospital.presentacion.dashboard;

import com.github.lgooddatepicker.components.DatePicker;
import hospital.logic.Medicamento;
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
        medicamentos_JPanel.setLayout(new java.awt.BorderLayout());
        recetas_JPanel.setLayout(new java.awt.BorderLayout());
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate desde = fechaDesde.getDate();
                LocalDate hasta = fechaHasta.getDate();
                String medicamento = (String) medicamentos_comboBox.getSelectedItem();

                // Add validation
                if (desde == null || hasta == null) {
                    JOptionPane.showMessageDialog(dashboard_JPanel,
                            "Por favor seleccione ambas fechas",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (desde.isAfter(hasta)) {
                    JOptionPane.showMessageDialog(dashboard_JPanel,
                            "La fecha 'Desde' no puede ser después de la fecha 'Hasta'",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    controller.cargarPrescripciones(desde, hasta, medicamento);

                    // Force immediate chart update after loading
                    SwingUtilities.invokeLater(() -> {
                        List<Prescripcion> prescripciones = model.getListaPrescripcion();
                        if (!prescripciones.isEmpty()) {
                            if (medicamento != null && !medicamento.equals("Todos")) {
                                updateChart(prescripciones, medicamento);
                            } else {
                                updateSummaryChart(prescripciones);
                            }
                            updatePieChart(prescripciones);
                        }
                    });

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

        // Clear existing items and add "Todos" first
        medicamentos_comboBox.removeAllItems();
        medicamentos_comboBox.addItem("Todos");

        // llenar combobox con los medicamentos disponibles
        Service service = Service.instance();
        List<Medicamento> medicamentos = service.loadListaMedicamentos();

        System.out.println("Medicamentos disponibles: " + medicamentos.size());
        medicamentos.forEach(m -> {
            String nombre = m.getNombre();
            System.out.println("Agregando medicamento: " + nombre);
            medicamentos_comboBox.addItem(nombre);
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
        System.out.println("Updating chart for: " + medicamentoSeleccionado);
        System.out.println("Prescripciones count: " + prescripciones.size());

        DefaultCategoryDataset dataset = buildDataset(prescripciones, medicamentoSeleccionado);
        System.out.println("Dataset row count: " + dataset.getRowCount());
        System.out.println("Dataset column count: " + dataset.getColumnCount());
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
                List<Prescripcion> prescripciones = model.getListaPrescripcion();
                System.out.println("Prescripciones loaded: " + prescripciones.size());
                if (!prescripciones.isEmpty()) {
                    System.out.println("First prescripcion: " + prescripciones.get(0).getId());
                    System.out.println("Recetas in first: " + prescripciones.get(0).getReceta().size());
                }

                // Update table
                TableModel tableModel = new TableModel(prescripciones);
                table1.setModel(tableModel);

                // Update charts
                String selectedMedicamento = (String) medicamentos_comboBox.getSelectedItem();
                if (selectedMedicamento != null && !selectedMedicamento.equals("Todos")) {
                    updateChart(prescripciones, selectedMedicamento);
                } else {
                    // If "Todos" is selected, show a summary chart or clear
                    updateSummaryChart(prescripciones);
                }

                updatePieChart(prescripciones);
            }
        }
    }

    private void updateSummaryChart(List<Prescripcion> prescripciones) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (prescripciones == null || prescripciones.isEmpty()) {
            // Clear the chart if no data
            medicamentos_JPanel.removeAll();
            medicamentos_JPanel.revalidate();
            medicamentos_JPanel.repaint();
            return;
        }

        TableModel tableModel = new TableModel(prescripciones);

        // Show top 5 medicamentos or all if less than 5
        int rowCount = Math.min(tableModel.getRowCount(), 5);
        for (int row = 0; row < rowCount; row++) {
            String medicamento = (String) tableModel.getValueAt(row, 0);
            for (int col = 1; col < tableModel.getColumnCount(); col++) {
                String mes = tableModel.getColumnName(col);
                Number cantidad = (Number) tableModel.getValueAt(row, col);
                if (cantidad.intValue() > 0) {
                    dataset.addValue(cantidad, medicamento, mes);
                }
            }
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Top Medicamentos por Mes",
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

}

