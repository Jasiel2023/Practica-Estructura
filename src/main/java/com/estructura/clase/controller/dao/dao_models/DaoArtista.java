package com.estructura.clase.controller.dao.dao_models;

import com.estructura.clase.controller.Utiles;
import com.estructura.clase.controller.dao.AdapterDao;
import com.estructura.clase.controller.data_structure.LinkedList;
import com.estructura.clase.base.models.Artista;

public class DaoArtista extends AdapterDao<Artista> {
    private Artista obj;

    public DaoArtista() {
        super(Artista.class);
        // TODO Auto-generated constructor stub
    }

    public Artista getObj() {
        if (obj == null)
            this.obj = new Artista();
        return this.obj;
    }

    public void setObj(Artista obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            // TODO
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            // TODO
            return false;
            // TODO: handle exception
        }
    }

    public LinkedList<Artista> orderLastName(Integer type) {
        LinkedList<Artista> lista = new LinkedList<>();
        if (!listAll().isEmpty()) {
            Integer cont = 0;
            long startTime = System.currentTimeMillis();
            Artista arr[] = listAll().toArray();
            int n = arr.length;
            if (type == Utiles.ASCEDENTE) {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].getNombre().toLowerCase()
                                .compareTo(arr[min_idx].getNombre().toLowerCase()) < 0) {
                            min_idx = j;
                            cont++;
                        }
                    }
                    Artista temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            } else {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++) {
                        if (arr[j].getNombre().toLowerCase()
                                .compareTo(arr[min_idx].getNombre().toLowerCase()) > 0) {
                            min_idx = j;
                            cont++;
                        }
                    }
                    Artista temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }

            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("se ha demorado " + endTime + " he hizo " + cont);
            lista.toList(arr);
        }
        return lista;
    }

    public LinkedList<Artista> orderLocate(Integer type) {
        LinkedList<Artista> lista = new LinkedList<>();
        if (!listAll().isEmpty()) {
            Integer cont = 0;
            long startTime = System.currentTimeMillis();
            Artista arr[] = listAll().toArray();
            int n = arr.length;
            if (type == Utiles.ASCEDENTE) {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].getNacionalidad().toLowerCase()
                                .compareTo(arr[min_idx].getNacionalidad().toLowerCase()) < 0) {
                            min_idx = j;
                            cont++;
                        }

                    Artista temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            } else {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].getNacionalidad().toLowerCase()
                                .compareTo(arr[min_idx].getNacionalidad().toLowerCase()) > 0) {
                            min_idx = j;
                            cont++;
                        }

                    Artista temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }

            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("se ha demorado " + endTime + " he hizo " + cont);
            lista.toList(arr);
        }
        return lista;
    }

    private int partition(Artista arr[], int begin, int end, Integer type) {
        // hashmap //clave - valor
        // Calendar cd = Calendar.getInstance();

        Artista pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Artista swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) > 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Artista swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Artista swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    private void quickSort(Artista arr[], int begin, int end, Integer type) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type);

            quickSort(arr, begin, partitionIndex - 1, type);
            quickSort(arr, partitionIndex + 1, end, type);
        }
    }

    public LinkedList<Artista> orderQ(Integer type) {
        LinkedList<Artista> lista = new LinkedList<>();
        if (!listAll().isEmpty()) {

            Artista arr[] = listAll().toArray();
            quickSort(arr, 0, arr.length - 1, type);
            lista.toList(arr);
        }
        return lista;
    }

    public static void main(String[] args) {
        DaoArtista da = new DaoArtista();
        // System.out.println(da.listAll().print());
        // ORDENADO
        // System.out.println("OTRDENADO");
        // System.out.println(da.orderQ(1).print());
        /*try {
            DaoCuenta dc = new DaoCuenta();
            HashMap mapa = dc.login("darkjoe@gmail.com", "1234");
            if(mapa != null) {
                System.out.println(mapa.get("usuario"));
            }
            for(Field f: Artista_Banda.class.getDeclaredFields()) {
                System.out.println(f.getName()+" "+f.getType().getSimpleName());
            }
        } catch (Exception ex) {
            System.out.println("Hubo un error "+ex);
            //ex.printStackTrace();
        }*/
        
          da.getObj().setId(da.listAll().getLength() + 1);
         da.getObj().setNacionalidad("Ecuatoriana");
         da.getObj().setNombre("viviana Cordova");
         if (da.save())
         System.out.println("GUARDADO");
         else
         System.out.println("Hubo un error");
          da.setObj(null);
         da.getObj().setId(da.listAll().getLength() + 1);
         da.getObj().setNacionalidad("Ecuatoriana");
          da.getObj().setNombre("Kerly Huachaca");
          if (da.save())
         System.out.println("GUARDADO");
          else
         System.out.println("Hubo un error");
         
    }

}
