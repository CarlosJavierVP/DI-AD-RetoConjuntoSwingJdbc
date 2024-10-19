package reto.models;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Usuario que modela al usuario de la base de datos
 * @author Carlos Javier
 * */
@Data
public class Usuario implements Serializable {
    /**identificador del usuario*/
    private Integer id;
    /**nombre del usuario*/
    private String nombre_usuario;
    /**contraseña del usuario*/
    private String password;

    /**lista de copias asociadas al usuario*/
    private List<Copia> micopia = new ArrayList<>(0);

    /**
     * Método Usuario para construir un Usuario sin parámetros
     * */
    public Usuario() {
    }

    /**
     * Método Usuario para construir un Usuario con parámetros
     * @param id del usuario
     * @param nombre_usuario del usuario
     * @param password del usuario
     * */
    public Usuario(Integer id, String nombre_usuario, String password) {
        this.id = id;
        this.nombre_usuario = nombre_usuario;
        this.password = password;
    }
}
