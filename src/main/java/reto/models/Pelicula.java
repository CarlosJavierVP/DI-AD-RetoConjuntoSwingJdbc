package reto.models;

import lombok.Data;
import java.io.Serializable;

/**
 * Clase Película que modela la película de la base de datos
 * @author Carlos Javier
 * */
@Data
public class Pelicula implements Serializable {
    /**Atributo identificador de la Película*/
    private Integer id;
    /**Atributo título de la Película*/
    private String titulo;
    /**Atributo género de la Película*/
    private String genero;
    /**Atributo año de la Película*/
    private Integer año;
    /**Atributo descripción de la Película*/
    private String descripcion;
    /**Atributo director de la Película*/
    private String director;

    /**
     * Método Película() para construir una Película sin parámetros
     * */
    public Pelicula() {
    }

    /**
     * Método Película para construir una Película con parámetros
     * @param id de la Película
     * @param titulo de la Película
     * @param genero de la Película
     * @param año de la Película
     * @param descripcion de la Película
     * @param director de la Película
     * */
    public Pelicula(Integer id, String titulo, String genero, Integer año, String descripcion, String director) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.año = año;
        this.descripcion = descripcion;
        this.director = director;
    }
}
