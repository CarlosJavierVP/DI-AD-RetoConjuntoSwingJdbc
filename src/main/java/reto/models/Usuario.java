package reto.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class Usuario implements Serializable {

    private Integer id;
    private String nombre_usuario;
    private String password;
}
