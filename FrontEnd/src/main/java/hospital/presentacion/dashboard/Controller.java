package hospital.presentacion.dashboard;

import hospital.logic.Prescripcion;
import hospital.logic.Service;

import java.time.LocalDate;
import java.util.List;

public class Controller {
    private Model model;
    private View2 view;

    public Controller(Model model, View2 view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
        this.view.setModel(model);
    }

//    public void refresh() {
////        model.loadData();
//        view.update();
//    }

    public void cargarPrescripciones(LocalDate desde, LocalDate hasta, String medicamento) {
        List<Prescripcion> result = Service.instance().getPrescripciones(desde, hasta, medicamento);

        System.out.println("Resultado tamaño: " + result.size());
        for (Prescripcion p : result) {
            System.out.println(p.getReceta() + " - " + p.getFechaRetiro());
        }
        model.setListaPrescripcion(result);   // ✅ en lugar de setCurrent
    }



}
