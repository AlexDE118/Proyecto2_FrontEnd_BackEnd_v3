package hospital.presentacion.despacho;

import hospital.logic.Prescripcion;
import hospital.logic.Service;

import java.util.List;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
        this.view.setModel(this.model);
    }

    public void buscarPrescripciones(String criterio) {
        List<Prescripcion> resultado = Service.instance().buscarPrescripciones(criterio);
        model.setPrescripciones(resultado);
    }

    public void actualizarEstado(Prescripcion prescripcion, String nuevoEstado) {
        prescripcion.setEstado(nuevoEstado);
        Service.instance().actualizarPrescripcion(prescripcion);
        model.setSeleccionada(prescripcion);

    }
}
