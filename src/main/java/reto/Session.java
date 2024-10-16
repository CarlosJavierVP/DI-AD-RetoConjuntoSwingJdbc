package reto;

import reto.models.Copia;
import reto.models.Pelicula;
import reto.models.Usuario;

import java.util.List;

/**
 * Clase Session para almacenar información
 */
public class Session {
    public static Usuario userSelected = null;
    public static Copia copySelected = null;
    public static List<Copia> copyDTO = null;
    public static Pelicula peliDTO = null;

    public static void paramsnotnull(){
        userSelected = null;
        copySelected = null;
        copyDTO = null;
        peliDTO = null;
    }


}
