package com.estructura.clase.controller.dao.dao_models;

import  com.estructura.clase.controller.dao.AdapterDao;
import  com.estructura.clase.base.models.Album;
import java.sql.Date;
import com.estructura.clase.controller.data_structure.LinkedList;

public class DaoAlbum extends AdapterDao<Album> {
    private Album obj;
    private LinkedList<Album> listAll;

    public DaoAlbum() {
        super(Album.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Album getObj() {
        if (obj == null) {
            this.obj = new Album();

        }
        return this.obj;
    }

    public void setObj(Album obj) {
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

    public LinkedList<Album> getListAll() {
        if (listAll == null) {
            listAll = listAll();
        }
        return listAll;
    }


    public static void main(String[] args) {
        DaoAlbum da = new DaoAlbum();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("Album 1");
        da.getObj().setFecha(new Date(2023, 10, 10));
        da.getObj().setId_banda(1);

        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
        System.out.println(da.getListAll().getLength());
        
    }
    

}