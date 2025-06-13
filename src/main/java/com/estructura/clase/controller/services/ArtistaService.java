package com.estructura.clase.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.hibernate.engine.spi.ActionQueue;

import com.estructura.clase.controller.dao.dao_models.DaoArtista;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.estructura.clase.base.models.Artista;
import com.estructura.clase.base.models.RolArtistaEnum;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.mappedtypes.Pageable;
import org.springframework.transaction.annotation.Propagation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.stereotype.Service;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
//@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class ArtistaService {
    private DaoArtista da;

    public ArtistaService() {
        da = new DaoArtista();
    }

    public void createArtista(@NotEmpty String nombre, @NotEmpty String nacionalidad) throws Exception {
        da.getObj().setNacionalidad(nacionalidad);
        da.getObj().setNombre(nombre);
        if (!da.save())
            throw new Exception("No se pudo guardar los datos de artista");
    }

    public void aupdateArtista(@NotEmpty Integer id, @NotEmpty String nombre, @NotEmpty String nacionalidad)
            throws Exception {
        da.setObj(da.listAll().get(id));
        da.getObj().setNacionalidad(nacionalidad);
        da.getObj().setNombre(nombre);
        if (!da.update(id))
            throw new Exception("No se pudo modificar los datos de artista");
    }

    public List<Artista> list(Pageable pageable) {
        return Arrays.asList(da.listAll().toArray());
    }

    public List<Artista> listAll() {
        // System.out.println("**********Entro aqui");
        // System.out.println("lengthy "+Arrays.asList(da.listAll().toArray()).size());
        return (List<Artista>) Arrays.asList(da.listAll().toArray());
    }

    public List<String> listCountry() {
        List<String> nacionalidades = new ArrayList<>();
        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            nacionalidades.add(locale.getDisplayCountry());
            // System.out.println("Country Code: " + locale.getCountry() + ", Country Name:
            // " + locale.getDisplayCountry());
        }

        return nacionalidades;
    }

    public List<String> listRolArtista() {
        List<String> lista = new ArrayList<>();
        for (RolArtistaEnum r : RolArtistaEnum.values()) {
            lista.add(r.toString());
        }
        return lista;
    }

    public List<Artista> order(String atributo, Integer type) {
        System.out.println(atributo + "  " + type);
        if (atributo.equalsIgnoreCase("nombres"))
            return (List<Artista>) Arrays.asList(da.orderQ(type).toArray());
        else if (atributo.equalsIgnoreCase("nacionalidad"))
            return (List<Artista>) Arrays.asList(da.orderLocate(type).toArray());
        else
            return (List<Artista>) Arrays.asList(da.listAll().toArray());
    }
}
