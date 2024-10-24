package reto;

import reto.models.Copia;
import reto.models.Pelicula;
import reto.models.Usuario;

import java.util.List;

/**
 * Clase Session para almacenar información
 * @author Carlos Javier
 */
public class Session {
    /**Atributo estático de Usuario para setear el usuario log y llamarlo cuando sea necesario*/
    public static Usuario userSelected = null;
    /**Atributo estático de Copia para setear una única copia y luego añadirla a la lista de copias del usuario*/
    public static Copia copySelected = null;
    /**Atributo estático de Lista de Copia, para setear todas las copias del usuario y mostrarla en principal*/
    public static List<Copia> copyDTO = null;
    /**Atributo estático de Pelicula para setear la Película y manejar sus datos*/
    public static Pelicula peliDTO = null;

    /**
     * Método estático para setear todos los atributos a nulo
     * */
    public static void paramsToNull(){
        userSelected = null;
        copySelected = null;
        copyDTO = null;
        peliDTO = null;
    }


}
