package model.DAO;

import java.util.List;

public interface GenericDao <T> {

    void insert(T o);
    void update(T o);
    void deleteById (Integer id);

    T findById (Integer id);
    List<T> findAll ();
}
