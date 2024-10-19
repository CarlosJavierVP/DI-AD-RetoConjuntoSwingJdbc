package reto.dao;

import reto.models.Copia;
import reto.models.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase CopiaDAO implementa la interfaz pasandole por plantilla la clase Copia
 * @author Carlos Javier
 * */
public class CopiaDAO implements DAO<Copia>{
    /**Sentencia SQL que devuelve todos los elementos de la tabla copia*/
    public static final String SELECT_FROM_COPIA = "select * from copia";
    /**Sentencia SQL que devuelve una copia de la tabla copia cuya id se le pasa por parámetro*/
    public static final String SELECT_FROM_COPIA_WHERE_ID = "select * from copia where id=?";
    /**Sentencia SQL que inserta una nueva copia en la tabla copia*/
    public static final String INSERT_INTO_COPIA = "insert into copia(estado,soporte,id_pelicula,id_usuario)values(?,?,?,?)";
    /**Sentencia SQL que modifica los elementos de una copia de la base de datos*/
    public static final String UPDATE_COPIA = "update copia set estado=?, soporte=? where id=?";
    /**Sentencia SQL que elimina una copia cuya id se le pasa por parámetro*/
    public static final String DELETE_FROM_COPIA = "delete from copia where id=?";
    /**Sentencia SQL que devuelve la lista de copias según el usuario que se le pasa por parámetro */
    public static final String SELECT_FROM_COPIA_WHERE_ID_USUARIO = "select * from copia where id_usuario=?";

    /**Conexión inicializada a null*/
    private static Connection con = null;

    /**
     * Método constructor que setea la conexión pasada por parámetro
     * @param conect que conecta con la base de datos
     * */
    public CopiaDAO (Connection conect){
        con = conect;
    }

    /**
     * Método que devuelve una lista con todas las copias
     * */
    @Override
    public List<Copia> findAll() {
        var lista = new ArrayList<Copia>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SELECT_FROM_COPIA);

            while(rs.next()){
                Copia copy = new Copia();
                copy.setId(rs.getInt("id"));
                copy.setEstado(rs.getString("estado"));
                copy.setSoporte(rs.getString("soporte"));
                copy.setId_pelicula(rs.getInt("id_pelicula"));
                copy.setId_usuario(rs.getInt("id_usuario"));
                lista.add(copy);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Método que devuelve una copia cuya id se le pasa por parámetro
     * @param id identificador de la copia
     * */
    @Override
    public Copia findById(Integer id) {
        Copia copy = null;

        try(PreparedStatement ps = con.prepareStatement(SELECT_FROM_COPIA_WHERE_ID)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                copy = new Copia();
                copy.setId(rs.getInt("id"));
                copy.setEstado(rs.getString("estado"));
                copy.setSoporte(rs.getString("soporte"));
                copy.setId_pelicula(rs.getInt("id_pelicula"));
                copy.setId_usuario(rs.getInt("id_usuario"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    /**
     * Método que persiste una nueva copia en la base de datos
     * @param copia nuevo objeto que se va a guardar
     * */
    @Override
    public void save(Copia copia) {
        try(PreparedStatement ps = con.prepareStatement(INSERT_INTO_COPIA, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,copia.getEstado());
            ps.setString(2,copia.getSoporte());
            ps.setInt(3,copia.getId_pelicula());
            ps.setInt(4,copia.getId_usuario());

            if(ps.executeUpdate() == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                Integer copia_id = keys.getInt(1);
                copia.setId(copia_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que modifica una copia en la base de datos
     * @param copia objeto que se va a modificar
     * */
    @Override
    public void update(Copia copia) {
        try(PreparedStatement ps = con.prepareStatement(UPDATE_COPIA)){
            ps.setString(1, copia.getEstado());
            ps.setString(2, copia.getSoporte());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que elimina la copia de la base de datos
     * @param copia objeto que se va a eliminar
     * */
    @Override
    public void delete(Copia copia) {
        try(PreparedStatement ps = con.prepareStatement(DELETE_FROM_COPIA)){
            ps.setInt(1, copia.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que devuelve una lista de copias de un usuario pasado por parámetro
     * @param u usuario que se le pasa por parámetro
     * */
    public List<Copia> findByUser(Usuario u) {
        Integer id = u.getId();
        var miLista = new ArrayList<Copia>(0);

        try(PreparedStatement ps = con.prepareStatement(SELECT_FROM_COPIA_WHERE_ID_USUARIO)){
            ps.setInt(1,id);
            var result = ps.executeQuery();

            while(result.next()){
                Copia copy = new Copia(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getInt(5)
                );
                copy.setId_usuario(id);
                miLista.add(copy);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return miLista;
    }

}
