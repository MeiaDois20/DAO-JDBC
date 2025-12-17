package application;

import java.util.Date;

import model.DAO.DaoFactory;
import model.DAO.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args) throws Exception {
        SellerDao sellerDao = new DaoFactory().createSellerDao();

        System.out.println("==== TEST 1: seller findById ====");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);
    }
}
