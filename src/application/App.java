package application;

import java.util.Date;
import java.util.List;

import model.DAO.DaoFactory;
import model.DAO.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args) throws Exception {

        SellerDao sellerDao = new DaoFactory().createSellerDao();

        System.out.println("\n==== TEST 1: seller findById ====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n==== TEST 2: seller findByDepartment ====");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        list.forEach(System.out::println);

        System.out.println("\n==== TEST 3: seller findAll ====");
        list = sellerDao.findAll();
        list.forEach(System.out::println);
    }
}
