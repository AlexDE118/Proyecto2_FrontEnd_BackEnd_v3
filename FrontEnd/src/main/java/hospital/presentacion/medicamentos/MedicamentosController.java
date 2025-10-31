package hospital.presentacion.medicamentos;

import hospital.logic.Medicamento;
import hospital.logic.Service;

public class MedicamentosController {
    View3 view;
    MedicamentosModel model;


    public MedicamentosController(View3 view,MedicamentosModel model){
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);

        loadMedicamentos();
    }

    public void createMedicamentos(Medicamento e) throws Exception {
        Service.instance().createMedicamentos(e);
    }

    public void readMedicamentos(String codigo) throws Exception {
        Medicamento e = new Medicamento();
        e.setCodigo(codigo);
        Service.instance().readMedicamentos(e);
    }

    public void updateMedicamentos(Medicamento e) throws Exception {
        Service.instance().updateMedicamentos(e);
    }

    public void loadMedicamentos(){
        model.setMedicamentos(Service.instance().loadListaMedicamentos());
    }

    public void deleteMedicamento(Medicamento medicamento) throws Exception{
        Service.instance().deleteMedicamento(medicamento);
    }

    public void searchMedicamento(String codigo){
        Medicamento e = new Medicamento();
        e.setCodigo(codigo);
        model.setMedicamentos(Service.instance().searchMedicamentoByCode(e));
    }
}
