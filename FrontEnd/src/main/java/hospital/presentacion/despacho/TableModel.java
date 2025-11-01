package hospital.presentacion.despacho;

import hospital.logic.Prescripcion;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel {
    public static final int PACIENTE = 0;
    public static final int PRESCRIPCION = 1;
    public static final int FECHA_RETIRO = 2;
    public static final int ESTADO = 3;



    private String[] colNames = {"Paciente", "Prescripción", "Fecha Retiro", "Estado"};
    private List<Prescripcion> rows;

    public TableModel(List<Prescripcion> rows) {
        this.rows = rows;
    }

    public void setRows(List<Prescripcion> rows) {
        this.rows = rows;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Prescripcion p = rows.get(row);
        return switch (col) {
            case 0 -> p.getPaciente().getNombre(); // Paciente
            case 1 -> p.getReceta().stream()       // Recetas → solo nombres de medicamentos
                    .map(r -> r.getMedicamentos().getNombre())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Sin medicamentos");
            case 2 -> p.getFechaRetiro(); // Fecha de retiro
            case 3 -> p.getEstado();      // Estado
            default -> "";
        };
    }
}
