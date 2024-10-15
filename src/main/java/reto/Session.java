package reto;

import reto.models.Copia;
import reto.models.Pelicula;
import reto.models.Usuario;

/**
 * Clase Session para almacenar información
 */
public class Session {
    public static Copia copySelected = null;
    public static Pelicula peliSelected = null;
    public static Usuario userSelected = null;

    public static void paramsnotnull(){
        userSelected = null;
    }


}
