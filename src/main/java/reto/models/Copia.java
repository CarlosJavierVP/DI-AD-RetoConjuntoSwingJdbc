package reto.models;

import lombok.Data;
import java.io.Serializable;

/**
 * Clase Copia que modela la copia de un usuario
 * @author Carlos Javier
 * */
@Data
public class Copia implements Serializable {
    /**Atributo id de la Copia*/
    private Integer id;
    /**Atributo estado de la Copia*/
    private String estado;
    /**Atributo soporte de la Copia*/
    private String soporte;
    /**Atributo id de la película asociada a la Copia*/
    private Integer id_pelicula;
    /**Atributo id del usuario asociado a la Copia*/
    private Integer id_usuario;

    /**Atributo usuario de la copia*/
    private Usuario u;
    /**Atributo Pelicula de la copia*/
    private Pelicula peli;

    /**
     * Método Copia para construir una copia sin parámetros
     * */
    public Copia() {
    }

    /**
     * Método Copia para construir una copia con parámetros
     * @param id identificador de la Copia
     * @param estado el estado en el que se encuentra la Copia
     * @param soporte formato en el que está la Copia
     * @param id_pelicula identificador de la película asociada a la Copia
     * @param id_usuario identificador del usuario asociado a la Copia
     * */
    public Copia(Integer id, String estado, String soporte, Integer id_pelicula, Integer id_usuario) {
        this.id = id;
        this.estado = estado;
        this.soporte = soporte;
        this.id_pelicula = id_pelicula;
        this.id_usuario = id_usuario;
    }

    /**
     * Método toString para mostrar la información de unos atributos concretos
     * */
    @Override
    public String toString() {
        return "Copia{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", soporte='" + soporte + '\'' +
                ", id_pelicula=" + id_pelicula +
                ", id_usuario=" + id_usuario +
                '}';
    }
}
