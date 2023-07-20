package net.schwarzbaer.java.spring.sems;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.schwarzbaer.java.spring.sems.address.AddressController;
import net.schwarzbaer.java.spring.sems.employee.EmployeeController;
import net.schwarzbaer.java.spring.sems.department.DepartmentController;
import net.schwarzbaer.java.spring.sems.ldap.LdapInspectController;

//@SuppressWarnings("unused")
@SpringBootTest
class SemsApplicationTests {

	@Autowired private LdapInspectController ldapInspectController;
	@Autowired private DepartmentController departmentController;
	@Autowired private EmployeeController employeeController;
	@Autowired private AddressController addressController;
	@Autowired private UserInterface userInterface;

	@Test
	void contextLoads() {
		Assertions.assertThat(ldapInspectController).isNotNull();
		Assertions.assertThat(departmentController).isNotNull();
		Assertions.assertThat(employeeController).isNotNull();
		Assertions.assertThat(addressController).isNotNull();
		Assertions.assertThat(userInterface).isNotNull();
	}

}
