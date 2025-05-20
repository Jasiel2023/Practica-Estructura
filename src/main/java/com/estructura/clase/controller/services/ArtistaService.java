package com.estructura.clase.controller.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.hibernate.engine.spi.ActionQueue;

import com.estructura.clase.controller.dao.dao_models.DaoArtista;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.estructura.clase.base.models.Artista;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.mappedtypes.Pageable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed

public class ArtistaService {
    
    private DaoArtista da;
    public ArtistaService() {
        da = new DaoArtista();
    }

    public void createArtista(@NotEmpty String nombre, @NotEmpty String nacionalidad) throws Exception{
            da.getObj().setNacionalidad(nacionalidad);
            da.getObj().setNombre(nombre);
            if (!da.save()) {
                throw new Exception("No se pudo guardar los datos del artista");
            }
    }

    public List<Artista> list(Pageable pageable){
        return Arrays.asList(da.listAll().toArray());
        
    }

    public List<Artista> listAll(){
        return (List<Artista>)Arrays.asList(da.listAll().toArray());
        
    }
    
    public List<String> listCountry(){
        List<String> nacionalidades = new ArrayList<>();
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes){
            Locale locale = new Locale("", countryCode);
            nacionalidades.add(locale.getDisplayCountry());
        }
        return nacionalidades;
    }

}
