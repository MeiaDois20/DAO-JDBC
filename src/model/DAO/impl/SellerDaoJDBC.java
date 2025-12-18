package model.DAO.impl;

import java.beans.Statement;
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
import model.DAO.SellerMapper;
import model.DAO.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

    private Connection conn;

    public SellerDaoJDBC (Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller s) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                "INSERT INTO seller " +
                "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
                "VALUES (?, ?, ?, ?, ?)",
                java.sql.Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setDate(3, java.sql.Date.valueOf(s.getBirthDate()));
            ps.setDouble(4, s.getBaseSalary());
            ps.setInt(5, s.getDepartment().getId());

            Integer rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    s.setId(id);
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
    public void update(Seller s) {
        
    }

    @Override
    public void deletedById(Integer id) {
        
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "WHERE seller.Id = ?"
            );

            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                Department dep = SellerMapper.instatiateDepartment(rs);
                Seller seller = SellerMapper.instatiateSeller(rs, dep);
                return seller;
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
    public List<Seller> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "ORDER BY Name"
            );

            rs = ps.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = SellerMapper.instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = SellerMapper.instatiateSeller(rs, dep);
                list.add(seller);
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

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "WHERE DepartmentId = ? " +
                "ORDER BY Name"
            );

            ps.setInt(1, department.getId());

            rs = ps.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = SellerMapper.instatiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = SellerMapper.instatiateSeller(rs, dep);
                list.add(seller);
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
