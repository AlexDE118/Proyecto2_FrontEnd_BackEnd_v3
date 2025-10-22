package hospital.presentacion.dashboard;

import hospital.logic.Prescripcion;
import hospital.logic.Receta;
import hospital.presentacion.AbstractTableModel;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableModel extends AbstractTableModel<Prescripcion> {

    private List<YearMonth> months; // Meses dinámicos según rango de fechas
    private List<String> medicamentos; // Lista de nombres de medicamentos
    private Map<String, Map<YearMonth, Integer>> data; // Medicamento -> (Mes -> Cantidad)
    private List<String> filteredMedicamentos; // Lista de medicamentos filtrados

    public TableModel(List<Prescripcion> rows) {
        super(new int[0], rows); // columnas vacías temporalmente
        preprocess();
        column = new int[months.size() + 1];
        for (int i = 0; i < column.length; i++) {
            column[i] = i;
        }
        initColNamesAfterPreprocess();
        filteredMedicamentos = new ArrayList<>(medicamentos); // inicialmente todos
    }

    private void preprocess() {
        months = rows.stream()
                .map(Prescripcion::getFechaRetiro)
                .map(YearMonth::from)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        medicamentos = rows.stream()
                .flatMap(p -> p.getReceta().stream())
                .map(r -> r.getMedicamentos().getNombre())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        data = new HashMap<>();
        for (String med : medicamentos) {
            Map<YearMonth, Integer> monthMap = new HashMap<>();
            for (YearMonth ym : months) {
                monthMap.put(ym, 0);
            }
            data.put(med, monthMap);
        }

        for (Prescripcion p : rows) {
            YearMonth ym = YearMonth.from(p.getFechaRetiro());
            for (Receta r : p.getReceta()) {
                String medName = r.getMedicamentos().getNombre();
                data.get(medName).put(ym, data.get(medName).get(ym) + r.getCantidad());
            }
        }
    }

    private void initColNamesAfterPreprocess() {
        columnName = new String[months.size() + 1];
        columnName[0] = "Medicamento";
        for (int i = 0; i < months.size(); i++) {
            YearMonth ym = months.get(i);
            columnName[i + 1] = ym.getYear() + "-" + ym.getMonthValue();
        }
    }

    @Override
    protected void initColNames() {
        // No hace nada
    }

    @Override
    protected Object getPropertyAt(Prescripcion prescripcion, int col) {
        return null;
    }

    @Override
    public int getRowCount() {
        return filteredMedicamentos.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String medName = filteredMedicamentos.get(rowIndex);
        if (columnIndex == 0) return medName;
        YearMonth ym = months.get(columnIndex - 1);
        return data.get(medName).getOrDefault(ym, 0);
    }

    // --- Nuevo método: filtrar por medicamento ---

    public void filterByMedicamento(String medName) {
        if (medName == null || medName.equalsIgnoreCase("Todos")) {
            filteredMedicamentos = new ArrayList<>(medicamentos);
        } else {
            filteredMedicamentos = medicamentos.stream()
                    .filter(m -> m.equalsIgnoreCase(medName))
                    .collect(Collectors.toList());
        }
        fireTableDataChanged(); // actualiza la tabla
    }

    // getters para llenar el JComboBox
    public List<String> getMedicamentos() {
        return medicamentos;
    }
}







