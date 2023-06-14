package net.schwarzbaer.java.spring.sems.employee;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {

    @Query("SELECT count(*) FROM #{#entityName} t where t.address.id = :addressId")
    Integer countByAddressID(@Param("addressId") Integer addressId);

    @Query("SELECT count(*) FROM #{#entityName} t where t.department.id = :departmentId")
    Integer countByDepartmentID(@Param("departmentId") Integer departmentId);
    
}
