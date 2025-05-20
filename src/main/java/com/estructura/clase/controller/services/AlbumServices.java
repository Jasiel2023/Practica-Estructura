package com.estructura.clase.controller.services;


import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.hibernate.engine.spi.ActionQueue;


import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.estructura.clase.controller.dao.dao_models.DaoAlbum;
import com.estructura.clase.base.models.Album;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.mappedtypes.Pageable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed

public class AlbumServices {
    
    private DaoAlbum da;
    public AlbumServices() {
        da = new DaoAlbum();
    }

    public void createAlbum(@NotEmpty String nombre, Integer id_banda, @NonNull Date Fecha) throws Exception{
            da.getObj().setNombre(nombre);
            da.getObj().setId_banda(id_banda);
            da.getObj().setFecha(Fecha);
            if (!da.save()) {
                throw new Exception("No se pudo guardar los datos del artista");
            }
    }

    public List<Album> list(Pageable pageable){
        return Arrays.asList(da.listAll().toArray());
        
    }

    public List<Album> listAll(){
        return (List<Album>)Arrays.asList(da.listAll().toArray());
        
    }
    

}
