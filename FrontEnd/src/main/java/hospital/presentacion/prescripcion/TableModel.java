package hospital.presentacion.prescripcion;

import hospital.logic.Receta;
import hospital.presentacion.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Receta> {
    public TableModel(int[] column, List<Receta> rows) {
        super(column, rows);
    }

    public static final int MEDICAMENTO = 0;
    public static final int CANTIDAD = 1;
    public static final int DURACION = 2;
    public static final int INDICACIONES = 3;

    @Override
    protected void initColNames(){
        columnName = new String[4];
        columnName[MEDICAMENTO] = "Medicamento";
        columnName[CANTIDAD] = "Cantidad";
        columnName[DURACION] = "Duración (días)";
        columnName[INDICACIONES] = "Indicaciones";
    }

    @Override
    protected Object getPropertyAt(Receta receta, int col) {
        switch(column[col]){
            case MEDICAMENTO: return receta.getMedicamentos().getNombre();
            case CANTIDAD: return receta.getCantidad();
            case DURACION: return receta.getDuracion();
            case INDICACIONES: return receta.getIndicaciones();
            default: return null;
        }
    }
}