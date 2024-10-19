package reto.dao;

import reto.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase UsuarioDAO implementa la interfaz pasandole por plantilla la clase Usuario
 * @author Carlos Javier
 * */
public class UsuarioDAO implements DAO<Usuario> {
    /**Sentencia SQL que devuelve una lista de usuarios de la tabla usuario*/
    public static final String SELECT_FROM_USUARIO = "select * from usuario";
    /**Sentencia SQL que devuelve un usuario de la tabla usuario según la id que se le pasa por parámetro*/
    public static final String SELECT_FROM_USUARIO_WHERE_ID = "select * from usuario where id=?";
    /**Sentencia SQL que inserta un nuevo usuario en la tabla usuario*/
    public static final String INSERT_INTO_USUARIO = "insert into usuario(nombre_usuario,password) values(?,?)";
    /**Sentencia SQL que modifica un usuario según el id que se le pasa por parámetro*/
    public static final String UPDATE_USUARIO = "update usuario set nombre_usuario=?, password=? where id=?";
    /**Sentencia SQL que elimina un usuario de la tabla usuario según el id que se le pasa por parámetro*/
    public static final String DELETE_FROM_USUARIO = "delete from usuario where id=?";
    /**Sentencia SQL que devuelve un usuario de la tabla usuario según el nombre y la contraseña que se le pasa por parámetro*/
    public static final String SELECT_USUARIO_PASSWORD_FROM_USUARIO = "select * from usuario where nombre_usuario=? and password=?";
    /**Sentencia SQL que devuelve un usuario de la tabla usuario según el nombre de usuario que se le pasa por parámetro*/
    public static final String SELECT_NOMBRE_USUARIO_FROM_USUARIO = "Select nombre_usuario from usuario where nombre_usuario=?";

    /**Conexión inicializada a null*/
    private static Connection con = null;

    /**
     * Método constructor que setea la conexión pasada por parámetro
     * @param conect que conecta con la base de datos
     * */
    public UsuarioDAO(Connection conect) {
        con = conect;
    }
    /**
     * Método que devuelve una lista con todos los usuarios
     * */
    @Override
    public List<Usuario> findAll() {
        var lista = new ArrayList<Usuario>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SELECT_FROM_USUARIO);

            while (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre_usuario(rs.getString("nombre_usuario"));
                user.setPassword(rs.getString("password"));
                lista.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Método que devuelve un usuario cuya id se le pasa por parámetro
     * @param id identificador del usuario
     * */
    @Override
    public Usuario findById(Integer id) {
        Usuario user = null;

        try (PreparedStatement ps = con.prepareStatement(SELECT_FROM_USUARIO_WHERE_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre_usuario(rs.getString("nombre_usuario"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * Método que persiste un nuevo usuario
     * @param usuario nuevo objeto que se va a guardar
     * */
    @Override
    public void save(Usuario usuario) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_INTO_USUARIO, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getPassword());

            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                usuario.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que modifica un usuario en la base de datos
     * @param usuario objeto que se va a modificar
     * */
    @Override
    public void update(Usuario usuario) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_USUARIO)) {
            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getPassword());
            ps.setInt(3, usuario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que elimina un usuario
     * @param usuario objeto que se va a eliminar
     * */
    @Override
    public void delete(Usuario usuario) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_FROM_USUARIO)) {
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para validar que el usuario existe en la base de datos y conectarte como tal
     * @param user nombre de usuario para conectarse
     * @param pass contraseña del usuario para conectarse
     * */
    public Usuario validateUser(String user, char[] pass) {
        Usuario usuario = null;
        try (PreparedStatement ps = con.prepareStatement(SELECT_USUARIO_PASSWORD_FROM_USUARIO)) {
            //He pasado la contraseña de una cadena char[] (que es propio del JPasswordField) a un String, con el wrapper no funcionaba
            String passString = new String(pass);

            ps.setString(1, user);
            ps.setString(2, passString);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                usuario.setPassword(rs.getString("password"));

                //usuario.setMicopia((new CopiaDAO(con).findUser(usuario)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    /**
     * Método para validar nuevo usuario y que el nombre no coincida con ninguno que exista en la base de datos
     * @param user nuevo nombre de usuario
     * */
    public Usuario validateNewUser(String user){
        Usuario usuario = null;
        try(PreparedStatement ps = con.prepareStatement(SELECT_NOMBRE_USUARIO_FROM_USUARIO)){
            ps.setString(1,user);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                usuario = new Usuario();
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }
}
