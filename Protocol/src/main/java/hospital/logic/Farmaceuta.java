package hospital.logic;

import java.io.Serializable;

public class Farmaceuta implements Serializable {
    //Atributos
    private String id;
    private String nombre;
    private String clave;
    //Constructores

    public Farmaceuta() {
        this.id = "";
        this.nombre = "";
        this.clave = "111";
    }
    public Farmaceuta(String nombre, String id, String clave) {
    //    super(nombre, id);
        this.nombre = nombre;
        this.id = id;
        this.clave = clave;
    }
    //Setters | Getters
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    //Metodos de la clase

    public String toString() {
        return nombre + " " + id + " " + clave;
    }
}
