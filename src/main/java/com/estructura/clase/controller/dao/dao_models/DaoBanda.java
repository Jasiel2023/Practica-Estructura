package com.estructura.clase.controller.dao.dao_models;

import com.estructura.clase.controller.dao.AdapterDao;

import java.sql.Date;

import com.estructura.clase.base.models.Banda;

public class DaoBanda extends AdapterDao<Banda>{
    private Banda obj;

    public DaoBanda(){
        super(Banda.class);

    }

    public Banda getObj() {
        if (obj == null) {
            this.obj = new Banda();
        }
        return this.obj;
    }

    public void setObj(Banda obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //LOG DE ERROR
            
            return false;
            // TODO: handle exception
        }

    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            //LOG DE ERROR
            
            return false;
            // TODO: handle exception
        }

    }


    public static void main(String[] args) {
        DaoBanda da = new DaoBanda();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Los Bukis");
        da.getObj().setFecha(new Date(2022, 02, 15));
        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
    }
}
