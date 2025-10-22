package hospital.presentacion;
import java.util.List;

public abstract class AbstractTableModel<E> extends javax.swing.table.AbstractTableModel implements javax.swing.table.TableModel {
    protected List<E> rows;
    protected int[] column;
    protected String[] columnName;

    public AbstractTableModel(int[] column, List<E> rows) {
        this.column = column;
        this.rows = rows;
        initColNames();
    }

    public int getColumnCount() {
        return column.length;
    }

    public String getColumnName(int col) {
        return columnName[column[col]];
    }

    public Class<?> getColumnClass(int col) {
        switch (column[col]) {
            default: return super.getColumnClass(col);
        }
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int row, int col) {
        E e = rows.get(row);
        return getPropertyAt(e,col);
    }

    protected abstract Object getPropertyAt(E e, int col);
    public E getRowAt(int row) {
        return rows.get(row);
    }
    protected abstract void initColNames();
}
