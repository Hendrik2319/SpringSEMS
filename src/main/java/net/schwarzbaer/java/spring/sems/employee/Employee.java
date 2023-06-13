package net.schwarzbaer.java.spring.sems.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import net.schwarzbaer.java.spring.sems.address.Address;
import net.schwarzbaer.java.spring.sems.department.Department;


@Entity
public class Employee {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String  forename;
    private String  surname;
    
    @ManyToOne
    private Address address;
    
    @ManyToOne
    private Department department;

    public void setForename  (String     forename  ) { this.forename   = forename;   }
    public void setSurname   (String     surname   ) { this.surname    = surname;    }
    public void setAddress   (Address    address   ) { this.address    = address;    }
    public void setDepartment(Department department) { this.department = department; }
    public String     getForename  () { return forename;   }
    public String     getSurname   () { return surname;    }
    public Address    getAddress   () { return address;    }
	public Department getDepartment() { return department; }
    
}
