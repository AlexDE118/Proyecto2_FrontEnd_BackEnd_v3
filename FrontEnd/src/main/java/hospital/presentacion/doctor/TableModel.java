package hospital.presentacion.doctor;

import hospital.logic.Doctor;
import hospital.presentacion.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Doctor> implements javax.swing.table.TableModel {
    public TableModel(int[] column, List<Doctor> rows) {
        super(column, rows);
    }

    public static final int ID = 0;
    public static final int NAME = 1;
    public static final int ESPECIALIAD = 2;

    @Override
    protected void initColNames() {
        columnName = new String[4];
        columnName[ID] = "ID";
        columnName[NAME] = "Nombre";
        columnName[ESPECIALIAD] = "Especialidad";
    }

    @Override
    protected Object getPropertyAt(Doctor medico, int col) {
        switch (column[col]) {
            case ID: return medico.getId();
            case NAME: return medico.getNombre();
            case ESPECIALIAD: return medico.getEspecialidad();
            default: return null;
        }
    }
}
