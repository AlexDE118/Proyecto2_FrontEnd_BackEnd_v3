package hospital.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescripcion {

    Paciente paciente;

    List<Receta> receta;
    private String estado; // "confeccionada", "proceso", "lista", "entregada"
    private LocalDate fechaConfeccion;
    private LocalDate fechaRetiro;
    private String id;

    public Prescripcion(){
        paciente = new Paciente();
        receta = new ArrayList<Receta>();
        estado = "";
        fechaConfeccion = LocalDate.now();
        fechaRetiro = LocalDate.now();
        id = "";
    }

    public Prescripcion(Paciente paciente, List<Receta> receta, String estado, LocalDate fechaConfeccion, LocalDate fechaRetiro) {
        this.paciente = paciente;
        this.receta = receta;
        this.estado = estado;
        this.fechaConfeccion = fechaConfeccion;
        this.fechaRetiro = fechaRetiro;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Receta> getReceta() {
        return receta;
    }

    public void setReceta(List<Receta> receta) {
        this.receta = receta;
    }


    public void addMedicamentoToReceta(Medicamento medicamento) {
    }

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public LocalDate getFechaConfeccion() {return fechaConfeccion;}
    public void setFechaConfeccion(LocalDate fechaConfeccion) {this.fechaConfeccion = fechaConfeccion;}

    public LocalDate getFechaRetiro() {return fechaRetiro;}
    public void setFechaRetiro(LocalDate fechaRetiro) {this.fechaRetiro = fechaRetiro;}

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
}
