package hospital.presentacion.usuario;

import hospital.logic.Usuario;
import hospital.presentacion.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Usuario> implements javax.swing.table.TableModel {

    public TableModel(int[] column, List<Usuario> rows) {
        super(column, rows);
    }

    public static final int ID = 0;
    public static final int MENSAJES = 1;

    @Override
    protected void initColNames(){
        columnName = new String[3];
        columnName[ID] = "ID";
        columnName[MENSAJES] = "Mensajes";
    }

    @Override
    protected Object getPropertyAt(Usuario usuario, int col) {
        switch(column[col]){
            case ID:
                return usuario.getId();
            case MENSAJES:
                if(usuario.getMessage() != null && !usuario.getMessage().trim().isEmpty()) {
                    return "Mensaje pendiente";
                } else {
                    return "No hay mensaje pendiente";
                }
            default:
                return null;
        }
    }

}