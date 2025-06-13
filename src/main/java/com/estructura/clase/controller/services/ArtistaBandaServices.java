package com.estructura.clase.controller.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.estructura.clase.controller.dao.dao_models.DaoArtista;
import com.estructura.clase.controller.dao.dao_models.DaoArtistaBanda;
import com.estructura.clase.controller.data_structure.LinkedList;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

@BrowserCallable
@AnonymousAllowed
public class ArtistaBandaServices {
    private DaoArtistaBanda db;
    public ArtistaBandaServices(){
        db = new DaoArtistaBanda();
    }
    public List<HashMap> listAll() throws Exception{
        
        return Arrays.asList(db.all().toArray());
    }

    public List<HashMap> order(String attribute, Integer type) throws Exception {
        return Arrays.asList(db.orderByArtist(type, attribute).toArray());
    } 

    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = db.search(attribute, text, type);
        if(!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }
    
    
}
