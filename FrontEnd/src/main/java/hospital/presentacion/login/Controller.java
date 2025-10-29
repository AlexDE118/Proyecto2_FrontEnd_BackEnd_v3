package hospital.presentacion.login;

import hospital.logic.Service;
import hospital.logic.Usuario;


public class Controller {

    View view;
    Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        model.addPropertyChangeListener(view);
    }

    // En LoginController
    public Usuario login(Usuario usuario) throws Exception {
//        // Buscar usuario en la lista
//        Usuario u = Service.instance().readUsuario(usuario);
//
//        // Validar clave
//        if (!u.getClave().equals(usuario.getClave())) {
//            throw new Exception("Clave incorrecta");
//        }
//
//        u.setLogged(true);
//        Service.instance().login(u);
        Usuario loggedUser = Service.instance().login(usuario);
        return loggedUser; // devuelvo el usuario completo
    }



}
