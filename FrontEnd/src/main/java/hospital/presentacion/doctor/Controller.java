package hospital.presentacion.doctor;
import hospital.logic.Doctor;
import hospital.logic.Service;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model){
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void readDoctor(String id) throws Exception{
        Doctor e = new Doctor();
        e.setId(id);
        model.setCurrent(Service.instance().readDoctor(e));
    }

    public void createDoctor(Doctor doctor) throws Exception{
        Doctor e = new Doctor();
        Service.instance().createDoctor(doctor);
    }

    public void loadDoctors() {
        model.setListaDoctores(Service.instance().loadListaDoctores());
    }

    public void deleteDoctor(Doctor doctor) throws Exception{
        Service.instance().deleteDoctor(doctor);
    }

    public void searchDoctor(String nombre){
        Doctor e = new Doctor();
        e.setNombre(nombre);
        model.setListaDoctores(Service.instance().searchDoctorByName(e));
    }
}
