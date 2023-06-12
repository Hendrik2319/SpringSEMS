package net.schwarzbaer.java.spring.sems.department;

import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepo extends CrudRepository<Department, Integer> {
    
}
