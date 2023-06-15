package net.schwarzbaer.java.spring.sems.employee;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {

    Integer countByAddressID(Integer addressId);
    Integer countByDepartmentID(Integer departmentId);
    
}
