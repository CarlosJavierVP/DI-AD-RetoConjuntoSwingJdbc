package models;

import lombok.Data;

@Data
public class Copia {
    private Integer id;
    private Integer id_pelicula;
    private Integer id_usuario;
    private String estado;
    private String soporte;
}
