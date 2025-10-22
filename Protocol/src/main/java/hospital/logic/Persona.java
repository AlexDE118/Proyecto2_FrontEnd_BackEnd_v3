package hospital.logic;

public class Persona {
    //Atributos
    protected String id;
    protected String nombre;

    //Constructorers
    public Persona() {
        this.nombre = "";
        this.id = "";
    }

    public Persona(String nombre, String id) {
        this.nombre = nombre;
        this.id = id;
    }

    //Setters | Getters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Metodos de la clase
}
