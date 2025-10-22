package hospital.logic;

public class Doctor extends Persona {
    //Atributos
    private String especialidad;
    private String clave;
    //Constructores

    public Doctor() {
        super("","");
        this.especialidad = "";
        this.clave = "111";
    }

    public Doctor(String nombre, String id, String especialidad, String clave) {
        super(nombre, id);
        this.especialidad = especialidad;
        this.clave = clave;
    }
    //Setters | Getters
    public String getEspecialidad() {
        return especialidad;
    }

    public String getClave() {
        return clave;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    //Metodos de la clase

    public String toString(){
        return nombre + " " + id + " " + especialidad + " " + clave;
    }
}
