package reto.dao;

import reto.models.Usuario;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {
    public static final String SELECT_FROM_USUARIO = "select * from usuario";
    public static final String SELECT_FROM_USUARIO_WHERE_ID = "select * from usuario where id=?";
    public static final String INSERT_INTO_USUARIO = "insert into usuario(nombre_usuario,password)";
    public static final String UPDATE_USUARIO = "update usuario set nombre_usuario=?, password=? where id=?";
    public static final String DELETE_FROM_USUARIO = "delete from usuario where id=?";
    public static final String SELECT_USUARIO_PASSWORD_FROM_USUARIO = "select * from usuario where nombre_usuario=? and password=?";
    private static Connection con = null;

    public UsuarioDAO(Connection c) {
        con = c;
    }


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

    @Override
    public void delete(Usuario usuario) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_FROM_USUARIO)) {
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    JTextField, JPassword

     */

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


}
