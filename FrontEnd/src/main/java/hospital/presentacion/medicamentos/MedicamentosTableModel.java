package hospital.presentacion.medicamentos;

import hospital.logic.Medicamento;
import hospital.presentacion.AbstractTableModel;

import java.util.List;

public class MedicamentosTableModel extends AbstractTableModel<Medicamento> implements javax.swing.table.TableModel{
    public MedicamentosTableModel(int[] column, List<Medicamento> rows) {
        super(column, rows);
    }

    public static final int CODIGO = 0;
    public static final int NOMBRE = 1;
    public static final int PRESENTACION = 2;

    @Override
    protected void initColNames(){
        columnName = new String[3];
        columnName[CODIGO] = "ID";
        columnName[NOMBRE] = "Nombre";
        columnName[PRESENTACION] = "PRESENTACION";
    }

    @Override
    protected Object getPropertyAt(Medicamento medicamento, int col) {
        switch(column[col]){
            case CODIGO: return medicamento.getCodigo();
            case NOMBRE: return medicamento.getNombre();
            case PRESENTACION: return medicamento.getPresentacion();
            default: return null;
        }
    }

    //End of class
}
