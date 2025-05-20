package com.estructura.clase.controller.dao.dao_models;

import  com.estructura.clase.controller.dao.AdapterDao;
import  com.estructura.clase.base.models.Cuenta;
import java.util.Date;
import com.estructura.clase.controller.data_structure.LinkedList;

public class DaoCuenta extends AdapterDao<Cuenta> {
    private Cuenta obj;
    private LinkedList<Cuenta> listAll;

    public DaoCuenta() {
        super(Cuenta.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Cuenta getObj() {
        if (obj == null) {
            this.obj = new Cuenta();

        }
        return this.obj;
    }

    public void setObj(Cuenta obj) {
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

    public LinkedList<Cuenta> getListAll() {
        if (listAll == null) {
            listAll = listAll();
        }
        return listAll;
    }


    public static void main(String[] args) {
        DaoCuenta da = new DaoCuenta();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setEmail("wagner@.com");
        da.getObj().setClave("xxxxx");
        da.getObj().setEstado(true);

        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
        System.out.println(da.getListAll().getLength());
        
    }
    

}