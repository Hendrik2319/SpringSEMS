package net.schwarzbaer.java.spring.sems.department;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import net.schwarzbaer.java.spring.sems.address.Address;
import net.schwarzbaer.java.spring.sems.employee.Employee;

@Entity
public class Department {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String  name;
    
	@ManyToOne
	private Address address;

    @OneToMany(mappedBy="department")
    Collection<Employee> employees;

	protected Department() {}

	public Department(String name, Address address) {
		this.name = name;
		this.address = address;
	}

	@Override
	public String toString() {
		return String.format(
            "Department[ id=%d, name=\"%s\", address=%s ]",
			id, name, address
		);
	}

    public String toLabel() {
		String str = String.format(
			"[%d] %s",
			id, name
		);
		if (address!=null) str += ", "+address.toLabel();
		return str;
    }

	public void setName   (String  name   ) { this.name    = name;    }
    public void setAddress(Address address) { this.address = address; }
    public Integer getID       () { return id;      }
    public String  getName     () { return name;    }
    public Address getAddress  () { return address; }
	public boolean hasAddress  () { return address!=null; }
	public Integer getEmployeesCount() { return employees.size(); }
}
