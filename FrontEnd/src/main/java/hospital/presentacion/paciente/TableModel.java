package hospital.presentacion.paciente;

import hospital.logic.Paciente;
import hospital.presentacion.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Paciente> implements javax.swing.table.TableModel {
    public TableModel(int[] column, List<Paciente> rows) {
        super(column, rows);
    }

    public static final int id = 0;
    public static final int NOMBRE = 1;
    public static final int TELEFONO = 2;

    @Override
    protected void initColNames(){
        columnName = new String[3];
        columnName[id] = "ID";
        columnName[NOMBRE] = "Nombre";
        columnName[TELEFONO] = "Telefono";
    }

    @Override
    protected Object getPropertyAt(Paciente paciente, int col) {
        switch(column[col]){
            case id: return paciente.getId();
            case NOMBRE: return paciente.getNombre();
            case TELEFONO: return paciente.getNumeroTelefono();
            default: return null;
        }
    }
}
