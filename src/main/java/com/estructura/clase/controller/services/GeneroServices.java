package com.estructura.clase.controller.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.hibernate.engine.spi.ActionQueue;

import com.estructura.clase.controller.dao.dao_models.DaoGenero;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.estructura.clase.base.models.Genero;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.mappedtypes.Pageable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed

public class GeneroServices {
    
    private DaoGenero da;
    public GeneroServices() {
        da = new DaoGenero();
    }

    public void createGenero(@NotEmpty String nombre) throws Exception{
            da.getObj().setNombre(nombre);
            if (!da.save()) {
                throw new Exception("No se pudo guardar los datos del artista");
            }
    }

    public List<Genero> list(Pageable pageable){
        return Arrays.asList(da.listAll().toArray());
        
    }

    public List<Genero> listAll(){
        return (List<Genero>)Arrays.asList(da.listAll().toArray());
        
    }
    

}
