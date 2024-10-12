package reto.models;

import lombok.Data;
import reto.dao.PeliculaDAO;

import java.util.ArrayList;
import java.util.List;

@Data
public class Copia {
    private Integer id;
    private String estado;
    private String soporte;
    private Integer id_pelicula;
    private Integer id_usuario;

    private Usuario u;
    private Pelicula peli;

    public Copia() {
    }

    public Copia(Integer id, String estado, String soporte, Integer id_pelicula, Integer id_usuario) {
        this.id = id;
        this.estado = estado;
        this.soporte = soporte;
        this.id_pelicula = id_pelicula;
        this.id_usuario = id_usuario;
    }


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
