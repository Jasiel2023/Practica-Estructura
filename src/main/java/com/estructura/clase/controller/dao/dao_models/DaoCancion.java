package com.estructura.clase.controller.dao.dao_models;

import  com.estructura.clase.controller.dao.AdapterDao;
import  com.estructura.clase.base.models.Cancion;
import java.util.Date;
import com.estructura.clase.controller.data_structure.LinkedList;

public class DaoCancion extends AdapterDao<Cancion> {
    private Cancion obj;
    private LinkedList<Cancion> listAll;

    public DaoCancion() {
        super(Cancion.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Cancion getObj() {
        if (obj == null) {
            this.obj = new Cancion();

        }
        return this.obj;
    }

    public void setObj(Cancion obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
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

    public LinkedList<Cancion> getListAll() {
        if (listAll == null) {
            listAll = listAll();
        }
        return listAll;
    }


    public static void main(String[] args) {
        DaoCancion da = new DaoCancion();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("OLIMPO");
        da.getObj().setId_genero(1);
        da.getObj().setDuracion(3);
        da.getObj().setUrl("https://www.youtube.com");
        da.getObj().setTipo(null);
        da.getObj().setId_album(1);

        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
        System.out.println(da.getListAll().getLength());
        
    }
    

}