package net.schwarzbaer.java.spring.sems.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String  forename;
    private String  surname;
    private Integer addressID;
    private Integer departmentID;

    public void setForename    (String  forename    ) { this.forename     = forename;     }
    public void setSurname     (String  surname     ) { this.surname      = surname;      }
    public void setAddressID   (Integer addressID   ) { this.addressID    = addressID;    }
    public void setDepartmentID(Integer departmentID) { this.departmentID = departmentID; }
    public String  getForename    () { return forename ; }
    public String  getSurname     () { return surname  ; }
    public Integer getAddressID   () { return addressID; }
	public Integer getDepartmentID() { return departmentID; }
    
}
