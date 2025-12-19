package application;

import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import model.DAO.DaoFactory;
import model.DAO.DepartmentDao;
import model.entities.Department;

public class App_Department {
    public static void main(String[] args) throws Exception {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: findById =======");
		Department dep = departmentDao.findById(1);
		System.out.println(dep);
		
		System.out.println("\n=== TEST 2: findAll =======");
		List<Department> list = departmentDao.findAll();
		list.forEach(System.out::println);

		System.out.println("\n=== TEST 3: insert =======");
		Department newDepartment = new Department(null, "food");
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New id: " + newDepartment.getId());

		System.out.println("\n=== TEST 4: update =======");
		Department dep2 = departmentDao.findById(1);
		dep2.setName("Food");
		departmentDao.update(dep2);
		System.out.println("Update completed");
		
		System.out.println("\n=== TEST 5: delete =======");
		departmentDao.deleteById(5);
		System.out.println("Delete completed");
    }
}
