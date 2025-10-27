package hospital.logic;

import java.io.Serializable;

public class Medicamento implements Serializable { //AA
    private String codigo;
    private String nombre;
    private String presentacion;

    //Constructores

    public Medicamento() {
        this.codigo = "";
        this.nombre = "";
        this.presentacion = "";
    }

    public Medicamento(String codigo, String nombre, String presentacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.presentacion = presentacion;
    }

    //Setters | Getters

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }
    //Metodos de la clase

    public String toString() {
        return codigo + " " + nombre + " " + presentacion;
    }
}
