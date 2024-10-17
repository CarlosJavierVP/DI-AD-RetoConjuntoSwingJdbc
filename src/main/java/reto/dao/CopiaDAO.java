package reto.dao;

import reto.models.Copia;
import reto.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CopiaDAO implements DAO<Copia>{
    public static final String SELECT_FROM_COPIA = "select * from Copia";
    public static final String SELECT_FROM_COPIA_WHERE_ID = "select * from copia where id=?";
    public static final String INSERT_INTO_COPIA = "insert into copia(estado,soporte,id_pelicula,id_usuario)values(?,?,?,?)";
    public static final String UPDATE_COPIA = "update copia set estado=?, soporte=? where id=?";
    public static final String DELETE_FROM_COPIA = "delete from copia where id=?";
    public static final String SELECT_FROM_COPIA_WHERE_ID_USUARIO = "select * from copia where id_usuario=?";

    private static Connection con = null;

    public CopiaDAO (Connection conect){
        con = conect;
    }



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
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                Integer copia_id = keys.getInt(1);
                Integer peli_id = keys.getInt(2);
                Integer user_id = keys.getInt(3);

                copia.setId(copia_id);
                copia.setId_pelicula(peli_id);
                copia.setId_usuario(user_id);
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
            ps.setInt(1, copia.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


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
