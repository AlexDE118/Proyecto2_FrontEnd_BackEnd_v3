package hospital.presentacion.historico;

import hospital.logic.Prescripcion;
import hospital.presentacion.AbstractTableModel;

import java.util.List;
public class TableModel extends AbstractTableModel<Prescripcion> implements javax.swing.table.TableModel {
    public TableModel(int[] columns, List<Prescripcion> rows) {
        super(columns, rows);
    }

    public static final int PACIENTE_ID = 0;
    public static final int PACIENTE_NOMBRE = 1;
    public static final int ESTADO = 2;

    @Override
    public void initColNames() {
        columnName = new String[3];
        columnName[PACIENTE_ID] = "ID";
        columnName[PACIENTE_NOMBRE] = "Paciente";
        columnName[ESTADO] = "Estado";
    }

    @Override
    protected Object getPropertyAt(Prescripcion prescripcion, int col) {
        switch (column[col]) {
            case PACIENTE_ID: return prescripcion.getPaciente().getId();
            case PACIENTE_NOMBRE: return prescripcion.getPaciente().getNombre();
            case ESTADO: return prescripcion.getEstado();
            default: return null;
        }
    }
}
