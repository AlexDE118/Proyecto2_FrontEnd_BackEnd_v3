package hospital.presentacion.historico;

import hospital.logic.Prescripcion;
import hospital.logic.Receta;
import hospital.logic.Service;

import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        model.setPrescripciones(Service.instance().loadListaPrescripciones());


    }

    public void clear(){
        model.setCurrent(new Prescripcion());
    }

    public void loadPrescripciones() {
        model.setPrescripciones(Service.instance().loadListaPrescripciones());
    }

    public void loadCurrentRecetas(){
        List<Receta> recetas = model.getCurrent().getReceta();
        model.setRecetas(recetas);
    }

    public void setCurrent(int row){

    }
}
