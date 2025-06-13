package com.estructura.clase.controller.dao.dao_models;

import com.estructura.clase.base.models.Genero;
import com.estructura.clase.controller.dao.AdapterDao;
import com.estructura.clase.controller.data_structure.LinkedList;

public class DaoGenero extends AdapterDao<Genero> {
    private Genero obj;

    private LinkedList<Genero> aux;

    public DaoGenero() {
        super(Genero.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Genero getObj() {
        if (obj == null) {
            this.obj = new Genero();

        }
        return this.obj;
    }

    public void setObj(Genero obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(this.listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {

            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {

            return false;
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        DaoGenero da = new DaoGenero();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Cumbia");

        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");
        }
        
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Vallenato");

        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");
        }

    
    }
}
