package application;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import model.DAO.DaoFactory;
import model.DAO.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class App {
    public static void main(String[] args) throws Exception {
        LocalDate localDate = LocalDate.now();

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

        System.out.println("\n==== TEST 4: seller Insert ====");
        Seller newSeller = new Seller(null, "Ana", "ana@gmail.com", localDate, 7000.0, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted, new id = " + newSeller.getId());

        System.out.println("\n==== TEST 5: seller Update ====");
        seller = sellerDao.findById(1);
        seller.setName("Thalys Ravel");
        seller.setEmail("thalys@gmail.com");
        sellerDao.update(seller);
        System.out.println("Update complete");

        System.out.println("\n==== TEST 6: seller Delete ====");
        sellerDao.deletedById(17);
        System.out.println("Delete complete");
    }
}
