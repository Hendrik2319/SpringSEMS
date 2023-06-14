package net.schwarzbaer.java.spring.sems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.schwarzbaer.java.spring.sems.address.Address;
import net.schwarzbaer.java.spring.sems.address.AddressRepo;
import net.schwarzbaer.java.spring.sems.department.Department;
import net.schwarzbaer.java.spring.sems.department.DepartmentRepo;
import net.schwarzbaer.java.spring.sems.employee.Employee;
import net.schwarzbaer.java.spring.sems.employee.EmployeeRepo;

@Controller
public class UserInterface {
    
	@Autowired private AddressRepo addressRepo;
	@Autowired private DepartmentRepo departmentRepo;
	@Autowired private EmployeeRepo employeeRepo;

	@GetMapping("/all_addresses")
	public String showAllAddresses(Model model) {
		model.addAttribute("allAddresses", addressRepo.findAll());
		return "all_addresses";
	}
    
	@GetMapping("/all_departments")
	public String showAllDepartments(Model model) {
		model.addAttribute("allDepartments", departmentRepo.findAll());
		return "all_departments";
	}
    
	@GetMapping("/all_employees")
	public String showAllEmployees(Model model) {
		model.addAttribute("allEmployees", employeeRepo.findAll());
		return "all_employees";
	}
    
	@GetMapping("/edit")
	public String showAddDepartmentView(Model model) {
		model.addAttribute("allAddresses", addressRepo.findAll());
		model.addAttribute("allEmployees", employeeRepo.findAll());
		model.addAttribute("allDepartments", departmentRepo.findAll());
		return "edit_db_view";
	}

	@PostMapping("/add_address")
	public String addAddress(
		@RequestParam(name="street" , required=true, defaultValue="unkown street") String  street,
		@RequestParam(name="house"  , required=true, defaultValue="12"           ) Integer housenumber,
		@RequestParam(name="town"   , required=true, defaultValue="unkown town"  ) String  town,
		@RequestParam(name="zipcode", required=true, defaultValue="123"          ) Integer zipcode
	) {
		Address address = new Address(street,housenumber,town,zipcode);
		addressRepo.save(address);
		return "redirect:/edit";
	}

	@PostMapping("/add_department")
	public String addDepartment(
		@RequestParam(name="name"   , required=true , defaultValue="New Department") String  name,
		@RequestParam(name="addr_id", required=false, defaultValue="-1"            ) Integer addrID
	) {
		Address address = addressRepo.findById(addrID).orElse(null);
		departmentRepo.save(new Department(name, address));
		return "redirect:/edit";
	}

	@PostMapping("/add_employee")
	public String addEmployee(
		@RequestParam(name="forename", required=true, defaultValue="<forename>") String  forename,
		@RequestParam(name="surname" , required=true, defaultValue="<surname>" ) String  surname,
		@RequestParam(name="addr_id" , required=false, defaultValue="-1"       ) Integer addrID,
		@RequestParam(name="dep_id"  , required=false, defaultValue="-1"       ) Integer depID
	) {
		Address    address    =    addressRepo.findById(addrID).orElse(null);
		Department department = departmentRepo.findById(depID ).orElse(null);
		employeeRepo.save(new Employee(forename, surname, address, department));
		return "redirect:/edit";
	}
}
