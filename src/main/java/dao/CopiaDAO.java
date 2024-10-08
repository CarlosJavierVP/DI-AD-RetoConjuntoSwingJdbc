package dao;

import models.Copia;
import models.Usuario;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CopiaDAO implements DAO<Copia>{
    public static final String SELECT_FROM_COPIA = "select * from Copia";
    public static final String SELECT_FROM_COPIA_WHERE_ID = "select * from copia where id=?";
    public static final String INSERT_INTO_COPIA = "insert into copia(estado,soporte,id_pelicula,id_usuario)";
    public static final String UPDATE_COPIA = "update copia set estado=?, soporte=? where id=?";
    public static final String DELETE_FROM_COPIA = "delete from copia where id=?";

    private static Connection con = null;

    public CopiaDAO (Connection conect){ con = conect;}



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

    @Override
    public void save(Copia copia) {
        try(PreparedStatement ps = con.prepareStatement(INSERT_INTO_COPIA, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,copia.getEstado());
            ps.setString(2,copia.getSoporte());
            ps.setInt(3,copia.getId_pelicula());
            ps.setInt(4,copia.getId_usuario());

            if(ps.executeUpdate() == 1){
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                copia.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Copia copia) {
        try(PreparedStatement ps = con.prepareStatement(UPDATE_COPIA)){
            ps.setString(1, copia.getEstado());
            ps.setString(2, copia.getSoporte());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Copia copia) {
        try(PreparedStatement ps = con.prepareStatement(DELETE_FROM_COPIA)){
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Copia> findUser(Usuario u) {
        var miLista = new ArrayList<Copia>();

        try(PreparedStatement ps = con.prepareStatement("select * from copia where id_usuario = '"+u.getId()+"'")){
            ps.setInt(4,u.getId());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Copia copy = new Copia();
                copy.setId(rs.getInt("id"));
                copy.setEstado(rs.getString("estado"));
                copy.setSoporte(rs.getString("soporte"));
                copy.setId_pelicula(rs.getInt("id_pelicula"));
                copy.setId_usuario(rs.getInt("id_usuario"));
                miLista.add(copy);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return miLista;
    }

    @Override
    public Object DataCon(String u, char[] p) {
        return null;
    }




}
