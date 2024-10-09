package reto.dao;


import reto.models.Usuario;

import java.util.List;

public interface DAO <T>{

    public List<T> findAll();
    public Object findById(Integer id);
    public void save(T t);
    public void update (T t);
    public void delete (T t);
    public Object DataCon (String u, char[] p);
    public List<T> findUser(Usuario u);
}
