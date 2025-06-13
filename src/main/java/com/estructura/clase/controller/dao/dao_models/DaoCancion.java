package com.estructura.clase.controller.dao.dao_models;

import com.estructura.clase.controller.Utiles;
import  com.estructura.clase.controller.dao.AdapterDao;
import com.estructura.clase.base.models.Artista;
import com.estructura.clase.base.models.Artista_Banda;
import  com.estructura.clase.base.models.Cancion;
import java.util.Date;
import java.util.HashMap;

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


    public LinkedList<HashMap<String, String>> all() throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            Cancion[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {

                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }


    private HashMap<String, String> toDict(Cancion arreglo) throws Exception {
        DaoCancion da = new DaoCancion();
        HashMap<String, String> aux = new HashMap<>();
        aux.put("id", arreglo.getId().toString());
        aux.put("nombre", arreglo.getNombre().toString());
        aux.put("duracion", arreglo.getDuracion().toString());
        aux.put("url", arreglo.getUrl().toString());
        aux.put("tipo", arreglo.getTipo().toString());
        aux.put("genero", new DaoGenero().listAll().get(arreglo.getId_genero() - 1).getNombre());
        aux.put("album", new DaoAlbum().listAll().get(arreglo.getId_album() - 1).getNombre());
        aux.put("id_genero", arreglo.getId_genero().toString()); 
        aux.put("id_album", arreglo.getId_album().toString());   
      return aux;
    }

   


    private int partition(HashMap<String, String>  arr[], int begin, int end, Integer type, String attribute) {
        // hashmap //clave - valor
        // Calendar cd = Calendar.getInstance();

        HashMap<String, String> pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().toLowerCase().compareTo(pivot.get(attribute).toLowerCase()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    HashMap<String, String> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().toLowerCase().compareTo(pivot.get(attribute).toLowerCase()) > 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    HashMap<String, String> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        HashMap<String, String> swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }


      private void quickSort(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type, attribute);

            quickSort(arr, begin, partitionIndex - 1, type, attribute);
            quickSort(arr, partitionIndex + 1, end, type, attribute);
        }
    }


    public LinkedList<HashMap<String, String>> orderByArtist(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        if (!lista.isEmpty()) {
            HashMap arr[] = lista.toArray();
            int n = arr.length;
            if (type == Utiles.ASCEDENTE) {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_idx].get(attribute).toString().toLowerCase()) < 0) {
                            min_idx = j;
                        }

                    HashMap temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            } else {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_idx].get(attribute).toString().toLowerCase()) > 0) {
                            min_idx = j;
                        }

                    HashMap temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }
        }
        return lista;
    }


     public LinkedList<HashMap<String, String>> orderQ(Integer type, String attribute)throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!all().isEmpty()) {

            HashMap<String, String> arr[] = all().toArray();
            quickSort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }
    // METODO DE BUSQUEDA


    public LinkedList<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();
        
        if (!lista.isEmpty()) {
           // 1. ordenamos la lista
           lista = orderQ(Utiles.ASCEDENTE, attribute);
           // 2. Tranformamon la lista a un arreglo
            HashMap<String, String>[] arr = lista.toArray();
            // 3. Extraemos el punto medio del arreglo
            Integer n = bynaryLineal(arr, attribute, text);
            System.out.println("LA N DE LA MITAD"+n);
            switch (type) {
                case 1:// Utiles START
                // escogemos la derecha
                if (n > 0) {
                    for (int i = n; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }else if (n < 0) {
                    // escogemos la izquierda desde 0 hasta n
                    n *= -1;
                    for (int i = 0; i < n; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                    
                } else {
                    // escogemos todo
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }
                    break;
                case 2:// Utiles.END
                // escogemos la derecha
                if (n > 0) {
                    for (int i = n; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }else if (n < 0) {
                    // escogemos la izquierda desde 0 hasta n
                    n *= -1;
                    for (int i = 0; i < n; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                    
                } else {
                    // escogemos todo
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }
                    break;
                default:
                  System.out.println(attribute+" "+text+" TRES");
              
                for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                break;
            }
        }
        return resp;
    }



    // METODO DE BUSQUEDA
    // MEtodo que permite saber desde donde se debe iniciar la busqueda
    private Integer bynaryLineal(HashMap<String, String>[] array, String attribute, String text){
        Integer half = 0;
        if (!(array.length == 0) && !text.isEmpty()) {
            half = array.length / 2;
            int aux = 0; 
            if (text.trim().toLowerCase().charAt(0) > array[half].get(attribute).toString().trim().toLowerCase().charAt(0)) 
                aux = 1;
             else if (text.trim().toLowerCase().charAt(0) < array[half].get(attribute).toString().trim().toLowerCase().charAt(0) ) 
                aux = -1;
            half = half * aux;
        }
        return half;
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





