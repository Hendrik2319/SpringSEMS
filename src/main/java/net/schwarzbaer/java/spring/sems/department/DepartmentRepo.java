package net.schwarzbaer.java.spring.sems.department;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepo extends CrudRepository<Department, Integer> {

    @Query("SELECT count(*) FROM #{#entityName} t where t.address.id = :addressId")
    Integer countByAddressID(@Param("addressId") Integer addressId);
    
}
