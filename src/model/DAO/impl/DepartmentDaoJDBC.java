package model.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.DAO.DepartmentDao;
import model.DAO.DbDaoMapper;
import model.entities.Department;
import model.entities.Seller;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC (Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department d) {

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                "INSERT INTO department " +
                "(Name) " +
                "VALUES (?)",
                java.sql.Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, d.getName());

            Integer rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    d.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("[ERROR] No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closePreparedStatement(ps);
        }
    }

    @Override
    public void update(Department d) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                "UPDATE department " +
                "SET Name = ? " +
                "WHERE Id = ?"
            );

            ps.setString(1, d.getName());
            ps.setInt(2, d.getId());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closePreparedStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                "DELETE FROM department " +
                "WHERE Id = ?"
            );

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new DbException("Department not found. Id: " + id);
            }
        }
        catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        }
        finally {
            DB.closePreparedStatement(ps);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                "SELECT * FROM department WHERE Id = ?"
            );

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Department dep = new Department();
                dep.setId(rs.getInt("Id"));
                dep.setName(rs.getString("Name"));
                return dep;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closePreparedStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                "SELECT * FROM department ORDER BY Name"
            );

            rs = ps.executeQuery();

            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                Department dep = new Department();
                dep.setId(rs.getInt("Id"));
                dep.setName(rs.getString("Name"));
                list.add(dep);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closePreparedStatement(ps);
            DB.closeResultSet(rs);
        }
    }

}
