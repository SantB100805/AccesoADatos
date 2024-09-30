package es.cheste.Unidad1.A4_Serializacion;

import java.io.Serial;
import java.io.Serializable;

public class Modulo implements Serializable {


    @Serial
    private static final long serialVersionUID = -5149745225679370080L;

    // Atributos del módulo
    private String moduls;
    private int hores;
    private double notes;

    // Constructor
    public Modulo(String moduls, int hores, double notes) {
        this.moduls = moduls;
        this.hores = hores;
        this.notes = notes;
    }

    // Getters y Setters
    public String getModuls() {
        return moduls;
    }

    public void setModuls(String moduls) {
        this.moduls = moduls;
    }

    public int getHores() {
        return hores;
    }

    public void setHores(int hores) {
        this.hores = hores;
    }

    public double getNotes() {
        return notes;
    }

    public void setNotes(double notes) {
        this.notes = notes;
    }



}
