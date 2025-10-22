package hospital.logic;

import java.util.ArrayList;
import java.util.List;

public class Despacho {
     List<Prescripcion> prescripciones;

     public Despacho() {
         prescripciones = new ArrayList<Prescripcion>();
     }

    public Despacho(List <Prescripcion> prescripciones) {
        this.prescripciones = new ArrayList<>();
    }

    public List<Prescripcion> getPrescripciones() {
        return prescripciones;
    }

    public void setPrescripciones(List<Prescripcion> prescripciones) {
        this.prescripciones = prescripciones;
    }

}