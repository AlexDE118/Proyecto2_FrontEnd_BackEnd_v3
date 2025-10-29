package hospital.logic;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String clave;
    private String userType;
    private String message;
    private boolean logged;

    public Usuario() {
        this.id = "";
        this.clave = "";
        this.userType = "";
        this.message = "";
        this.logged = false;
    }

    public Usuario(String id, String clave, String userType) {
        this.id = id;
        this.clave = clave;
        this.userType = userType;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

}
