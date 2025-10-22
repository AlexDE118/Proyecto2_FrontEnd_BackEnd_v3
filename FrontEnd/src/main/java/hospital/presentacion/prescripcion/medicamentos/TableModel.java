package hospital.presentacion.prescripcion.medicamentos;

import hospital.logic.Medicamento;
import hospital.presentacion.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Medicamento> implements javax.swing.table.TableModel{
    public TableModel(int[] column, List<Medicamento> row){
        super(column, row);
    }

    public static final int ID = 0;
    public static final int NAME = 1;
    public static final int PRESENTACION = 2;

    @Override
    protected void initColNames(){
        columnName = new String[3];
        columnName[ID] = "ID";
        columnName[NAME] = "Nombre";
        columnName[PRESENTACION] = "Presentacion";
    }

    @Override
    protected Object getPropertyAt(Medicamento medicamento, int col) {
        switch(column[col]){
            case ID: return medicamento.getCodigo();
            case NAME: return medicamento.getNombre();
            case PRESENTACION: return medicamento.getPresentacion();
            default: return null;
        }
    }
}
