package net.schwarzbaer.java.spring.sems.department;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Department {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String  name;
    private Integer addressID;

	public void setName     (String  name     ) { this.name      = name;      }
    public void setAddressID(Integer addressID) { this.addressID = addressID; }
    public String  getName     () { return name ;     }
    public Integer getAddressID() { return addressID; }
    
}
