package reto.dao;

import reto.models.Pelicula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase PeliculaDAO implementa la interfaz pasandole por plantilla la clase Película
 * @author Carlos Javier
 * */
public class PeliculaDAO implements DAO<Pelicula>{
    /**Sentencia SQL que devuelve una lista de peliculas de la tabla pelicula*/
    public static final String SELECT_FROM_PELICULA = "select * from pelicula";
    /**Sentencia SQL que devuelve una pelicula de la tabla pelicula según el id que se le pasa por parámetro*/
    public static final String SELECT_FROM_PELICULA_WHERE_ID = "select * from pelicula where id=?";
    /**Sentencia SQL que inserta una nueva pelicula en la tabla pelicula*/
    public static final String INSERT_INTO_PELICULA = "insert into pelicula(titulo,genero,año,descripcion,director)";
    /**Sentencia SQL que modifica una pelicula de la tabla pelicula según el id que se le pasa por parámetro*/
    public static final String UPDATE_PELICULA = "update pelicula set titulo=?, genero=?, año=?, descripcion=?, director=? where id=?";
    /**Sentencia SQL que elimina una película según el id que se le pasa por parámetro*/
    public static final String DELETE_FROM = "delete from pelicula where id=?";
    /**Sentencia SQL que devuelve una película según el título que se le pasa por parámetro*/
    public static final String SELECT_FROM_PELICULA_WHERE_TITULO = "Select * from pelicula where titulo=?";

    /**Conexión inicializada a null*/
    private static Connection con = null;

    /**
     * Método constructor que setea la conexión pasada por parámetro
     * @param conect que conecta con la base de datos
     * */
    public PeliculaDAO(Connection conect){
        con = conect;
    }

    /**
     * Método que devuelve una lista con todas las películas
     * */
    @Override
    public List<Pelicula> findAll() {
        var lista = new ArrayList<Pelicula>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SELECT_FROM_PELICULA);

            while(rs.next()){
                Pelicula peli = new Pelicula();
                peli.setId(rs.getInt("id"));
                peli.setTitulo(rs.getString("titulo"));
                peli.setGenero(rs.getString("genero"));
                peli.setAño(rs.getInt("año"));
                peli.setDescripcion(rs.getString("descripcion"));
                peli.setDirector(rs.getString("director"));
                lista.add(peli);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Método que devuelve una película cuya id se le pasa por parámetro
     * @param id identificador de la película
     * */
    @Override
    public Pelicula findById(Integer id) {
        Pelicula peli = null;

        try(PreparedStatement ps = con.prepareStatement(SELECT_FROM_PELICULA_WHERE_ID)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                peli = new Pelicula();
                peli.setId(rs.getInt("id"));
                peli.setTitulo(rs.getString("titulo"));
                peli.setGenero(rs.getString("genero"));
                peli.setAño(rs.getInt("año"));
                peli.setDescripcion(rs.getString("descripcion"));
                peli.setDirector(rs.getString("director"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return peli;
    }

    /**
     * Método que persiste una nueva película
     * @param pelicula nuevo objeto que se va a guardar
     * */
    @Override
    public void save(Pelicula pelicula) {
        try(PreparedStatement ps = con.prepareStatement(INSERT_INTO_PELICULA, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,pelicula.getTitulo());
            ps.setString(2, pelicula.getGenero());
            ps.setInt(3,pelicula.getAño());
            ps.setString(4,pelicula.getDescripcion());
            ps.setString(5,pelicula.getDirector());

            if(ps.executeUpdate() == 1){
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                pelicula.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que modifica una pelicula en la base de datos
     * @param pelicula objeto que se va a modificar
     * */
    @Override
    public void update(Pelicula pelicula) {
        try(PreparedStatement ps = con.prepareStatement(UPDATE_PELICULA)){
            ps.setString(1,pelicula.getTitulo());
            ps.setString(2,pelicula.getGenero());
            ps.setInt(3,pelicula.getAño());
            ps.setString(4, pelicula.getDescripcion());
            ps.setString(5, pelicula.getDirector());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que elimina la pelicula
     * @param pelicula objeto que se va a eliminar
     * */
    @Override
    public void delete(Pelicula pelicula) {
        try(PreparedStatement ps = con.prepareStatement(DELETE_FROM)){
            ps.setInt(1,pelicula.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que devuelve una película según el título que se le pasa por parámetro
     * @param title de la película que se va a devolver
     * */
    public Pelicula findByTitle(String title){
        Pelicula peli = null;
        try(PreparedStatement ps = con.prepareStatement(SELECT_FROM_PELICULA_WHERE_TITULO)){
            ps.setString(1,title);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                peli = new Pelicula(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return peli;
    }
}
