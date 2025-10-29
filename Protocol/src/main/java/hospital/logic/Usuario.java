package hospital.logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    private String id;
    private String clave;
    private String userType;
    private ArrayList<String> mensajes;

    public Usuario() {
        this.id = "";
        this.clave = "";
        this.userType = "";
        this.mensajes = new ArrayList<>();
    }

    public Usuario(String id, String clave, String userType) {
        this.id = id;
        this.clave = clave;
        this.userType = userType;
        this.mensajes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public ArrayList<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<String> mensajes) {
        this.mensajes = mensajes;
    }

}
