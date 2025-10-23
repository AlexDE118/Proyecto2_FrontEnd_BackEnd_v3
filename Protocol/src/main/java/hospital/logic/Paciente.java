package hospital.logic;

import java.io.Serializable;

public class Paciente implements Serializable {
    //Attritubtos
    String id;
    String nombre;
    //private LocalDate fechaNacimiento;
    private String numeroTelefono;

    //Constructores

    public Paciente() {
        this.nombre="";
        this.id = "";
        //this.fechaNacimiento = "";
        this.numeroTelefono = "0000-0000";
    }

    public Paciente(String nombre, String id, String numeroTelefono) {
        this.id = id;
        this.nombre = nombre;
        //this.fechaNacimiento = fechaNacimiento;
        this.numeroTelefono = numeroTelefono;
    }

//    public Paciente(String nombre, String id, String numeroTelefono, LocalDate fechaNacimiento) {
//        this.id = id;
//        this.nombre = nombre;
//        //this.fechaNacimiento = fechaNacimiento;
//        this.numeroTelefono = numeroTelefono;
//    }

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

//    public void setFechaNacimiento(LocalDate fechaNacimiento) {
//        this.fechaNacimiento = fechaNacimiento;
//    }
//
//    public LocalDate getFechaNacimiento() {
//        return fechaNacimiento;
//    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    //Metodos de la clase
    public String toString() {
        return this.nombre + "\n"
                + this.id + "\n"
    //            + this.fechaNacimiento.toString() + "\n"
                + this.numeroTelefono;
    }
}
