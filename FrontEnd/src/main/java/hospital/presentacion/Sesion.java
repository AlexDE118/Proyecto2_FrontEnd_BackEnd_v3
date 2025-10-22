package hospital.presentacion;

import hospital.logic.Usuario;

public class Sesion {
    private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        Sesion.usuario = usuario;
    }

    public static void logout() {
        usuario = null;
    }

    public static boolean isLoggedIn(){
        return usuario != null;
    }
}
