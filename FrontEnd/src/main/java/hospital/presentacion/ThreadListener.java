package hospital.presentacion;

import hospital.logic.Usuario;

public interface ThreadListener {
    //public void refresh();
    public void deliver_message(String message);
    public void deliver_login(Usuario usuario);
    public void deliver_logout(Usuario usuario);
    public void deliver_message_status_change(Usuario usuario);
}
