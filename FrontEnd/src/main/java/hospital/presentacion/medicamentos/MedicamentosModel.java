package hospital.presentacion.medicamentos;

import hospital.logic.Medicamento;
import hospital.presentacion.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MedicamentosModel extends AbstractModel {
    Medicamento currentMedicamento;
    List<Medicamento> medicamentos = new ArrayList<>();

    public static final String CURRENT = "Current";
    public static final String LISTAMEDICAMENTOS = "Medicamentos";

    public MedicamentosModel() {
        currentMedicamento = new Medicamento();
        medicamentos = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener){
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LISTAMEDICAMENTOS);
    }

    public Medicamento getCurrentMedicamento() {
        return currentMedicamento;
    }

    public void setCurrentMedicamento(Medicamento currentMedicamento) {
        this.currentMedicamento = currentMedicamento;
        firePropertyChange(CURRENT);
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
        firePropertyChange(LISTAMEDICAMENTOS);
    }


}
