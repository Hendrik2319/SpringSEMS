package net.schwarzbaer.java.spring.sems.department;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import net.schwarzbaer.java.spring.sems.address.Address;

@Entity
public class Department {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String  name;
    
	@ManyToOne
	private Address address;

	public void setName   (String  name   ) { this.name    = name;    }
    public void setAddress(Address address) { this.address = address; }
    public String  getName   () { return name ;     }
    public Address getAddress() { return address; }
    
}
