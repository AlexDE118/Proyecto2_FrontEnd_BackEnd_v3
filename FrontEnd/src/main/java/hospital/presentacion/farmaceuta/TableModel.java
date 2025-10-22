package hospital.presentacion.farmaceuta;

import hospital.logic.Farmaceuta;
import hospital.presentacion.AbstractTableModel;

import java.util.List;

public class TableModel extends AbstractTableModel<Farmaceuta> implements javax.swing.table.TableModel {
    public TableModel(int[] column, List<Farmaceuta> rows) {
        super(column, rows);
    }

    public static final int ID = 0;
    public static final int NAME = 1;

    @Override
    protected void initColNames(){
        columnName = new  String[2];
        columnName[ID] = "ID";
        columnName[NAME] = "Nombre";
    }

    @Override
    protected Object getPropertyAt(Farmaceuta farmaceuta, int col){
        switch(column[col]){
            case ID: return farmaceuta.getId();
            case NAME: return farmaceuta.getNombre();
            default: return null;
        }
    }
}
