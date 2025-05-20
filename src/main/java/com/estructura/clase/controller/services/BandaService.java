package com.estructura.clase.controller.services;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import com.estructura.clase.controller.dao.dao_models.DaoBanda;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;

import com.estructura.clase.base.models.Banda;

@BrowserCallable
@AnonymousAllowed

public class BandaService {
    private DaoBanda db;

    public BandaService(){
        db= new DaoBanda();
    }


    public void createBanda(@NotEmpty String nombre, @NonNull Date fecha) throws Exception{
        if (nombre.trim().length() > 0 && fecha.toString().length() > 0) {
            db.getObj().setNombre(nombre);;
            db.getObj().setFecha(fecha);
            if (!db.save()) {
                throw new Exception("No se pudo guardar los datos de la banda");
            }
        }
    }

    public void updateBanda(@NotEmpty Integer id,@NotEmpty String nombre, @NonNull Date fecha) throws Exception{
        if (id != null && id > 0 && nombre.trim().length() > 0 && fecha.toString().length() > 0) {
            db.setObj(db.listAll().get(id - 1));
            db.getObj().setNombre(nombre);;
            db.getObj().setFecha(fecha);
            if (!db.update(id - 1)) {
                throw new Exception("No se pudo modificar los datos de la banda");
            }
        }
    }

    public List<Banda> listAllBanda(){
        return Arrays.asList(db.listAll().toArray());
        
    }

}
