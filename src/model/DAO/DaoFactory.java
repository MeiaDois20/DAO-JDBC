package model.DAO;

import model.DAO.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC();
    }

    // public static DepartmentDao createDepartmentDao() {
    //     return new DepartmentDaoJDBC();
    // }
}
