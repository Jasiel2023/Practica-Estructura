package com.estructura.clase.base.models;

import java.sql.Date;
import java.util.HashMap;

public class Banda {
    private Integer id;
    private String nombre;
    private Date fecha;

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Banda copy(Banda obj) {
        Banda aux = new Banda();
        aux.fecha = obj.getFecha();
        aux.id = obj.getId();
        aux.nombre = obj.getNombre();
        return aux;
    }

  public HashMap<String, Object> toDict() {
        HashMap<String, Object> diccionario = new HashMap<>();
        diccionario.put("id", this.getId());
        diccionario.put("fecha", this.getFecha());
        diccionario.put("nombre", this.getNombre());
        return diccionario;
    }



}
