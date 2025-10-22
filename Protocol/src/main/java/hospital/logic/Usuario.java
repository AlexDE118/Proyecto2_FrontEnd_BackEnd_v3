package hospital.logic;

public class Usuario {
    private String id;
    private String clave;
    private String userType;

    public Usuario() {
        this.id = "";
        this.clave = "";
        this.userType = "";
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

}
