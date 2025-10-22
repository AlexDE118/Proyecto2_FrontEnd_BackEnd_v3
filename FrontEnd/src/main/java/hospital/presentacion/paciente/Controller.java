package hospital.presentacion.paciente;

import hospital.logic.Paciente;
import hospital.logic.Service;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view, Model model){
        this.view=view;
        this.model=model;
        view.setController(this);
        view.setModel(model);
        model.addPropertyChangeListener(view);
    }

    public void createPaciente(Paciente p) throws Exception{
        Service.instance().createPaciente(p);
    }

    public void loadPacientes() {
        model.setListaPacientes(Service.instance().loadListaPacientes());
    }

    public void deletePaciente(Paciente p) throws Exception {
        Service.instance().deletePaciente(p);
    }

    public void searchPaciente(String nombre) throws Exception{
        Paciente p = new Paciente();
        p.setNombre(nombre);
        model.setListaPacientes(Service.instance().searchPaciente(p));
    }

}
