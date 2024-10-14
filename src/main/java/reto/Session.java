package reto;

import com.mysql.cj.xdevapi.UpdateStatementImpl;
import reto.models.Copia;
import reto.models.Pelicula;
import reto.models.Usuario;

/**
 * Clase reto.Session para almacenar información y pasarla a otra ventana
 */
public class Session {
    public static Copia copySelected = null;
    public static Pelicula peliSelected = null;

    public static Usuario userSelected = null;


}
