package model.DAO;

import java.lang.classfile.constantpool.IntegerEntry;
import java.util.List;

import model.entities.Seller;

public interface SellerDao {

    void insert(Seller s);
    void update(Seller s);
    void deletedById (Integer id);

    Seller findById (Integer id);
    List<Seller> findAll ();
}
