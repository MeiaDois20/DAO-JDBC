package model.DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

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
        return null;
    }
}
