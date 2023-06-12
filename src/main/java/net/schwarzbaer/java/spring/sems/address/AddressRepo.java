package net.schwarzbaer.java.spring.sems.address;

import org.springframework.data.repository.CrudRepository;

public interface AddressRepo extends CrudRepository<Address, Integer> {
    
}
