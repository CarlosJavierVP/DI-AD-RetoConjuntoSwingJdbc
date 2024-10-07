package dao;

import models.Usuario;

import java.util.List;
import java.util.Objects;

public interface DAO <T>{

    public List<T> findAll();
    public Object findById(Integer id);
    public void save(T t);
    public void update (T t);
    public void delete (T t);


}
