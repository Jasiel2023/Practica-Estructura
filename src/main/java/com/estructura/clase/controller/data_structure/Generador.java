package com.estructura.clase.controller.data_structure;

public class Generador {
    private String id;
    private String tipo;
    private double consumo;

    public Generador(String id, String tipo, double consumo) {
        this.id = id;
        this.tipo = tipo;
        this.consumo = consumo;
    }

    public String getId() { return id; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Generador)) return false;
        Generador g = (Generador) obj;
        return id.equals(g.id); // solo comparo por ID
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id + "," + tipo + "," + consumo;
    }
}
