package model.DAO;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

    void insert(Department d);
    void update(Department d);
    void deletedById (Integer id);

    Department findById (Integer id);
    List<Department> findAll ();
}
