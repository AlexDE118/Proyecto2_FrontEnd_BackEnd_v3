package hospital.logic;


import java.io.Serializable;

public class Receta implements Serializable {
    Medicamento medicamentos;
    int numero;
    int cantidad;
    int duracion;
    String indicaciones;



    public Receta(){
        medicamentos = new Medicamento();
        numero = 0;
        cantidad = 0;
        duracion = 0;
        indicaciones = "";


    }

    public Receta(Medicamento medicamento, int cantidad, int duracion, String indicaciones){
        medicamentos = medicamento;
        this.cantidad = cantidad;
        this.duracion = duracion;
        this.indicaciones = indicaciones;


    }

    public Medicamento getMedicamentos(){
        return medicamentos;
    }
    public void setMedicamentos(Medicamento medicamentos){
        this.medicamentos = medicamentos;
    }

    public int getCantidad(){
        return cantidad;
    }

    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    public int getDuracion(){
        return duracion;
    }

    public void setDuracion(int duracion){
        this.duracion = duracion;
    }

    public String getIndicaciones(){
        return indicaciones;
    }
    public void setIndicaciones(String indicaciones){
        this.indicaciones = indicaciones;
    }

    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
}
