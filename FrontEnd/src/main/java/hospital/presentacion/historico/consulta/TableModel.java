package hospital.presentacion.historico.consulta;

import hospital.logic.Receta;
import hospital.presentacion.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Receta> implements javax.swing.table.TableModel {
    public TableModel(int[] cols, List<Receta> rows) {
        super(cols,rows);
    }

    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int Cantidad = 2;
    public static final int DURACION = 3;

    @Override
    protected void initColNames(){
        columnName = new String[4];
        columnName[ID] =  "ID";
        columnName[NOMBRE] = "NOMBRE";
        columnName[Cantidad] = "CANTIDAD";
        columnName[DURACION] = "DURACION";
    }

    @Override
    protected Object getPropertyAt(Receta receta, int columnIndex) {
        switch (column[columnIndex]) {
            case ID: return receta.getMedicamentos().getCodigo();
            case NOMBRE: return receta.getMedicamentos().getNombre();
            case Cantidad: return receta.getCantidad();
            case DURACION: return receta.getDuracion();
            default: return null;
        }
    }
}
