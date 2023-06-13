package net.schwarzbaer.java.spring.sems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.schwarzbaer.java.spring.sems.address.Address;
import net.schwarzbaer.java.spring.sems.address.AddressRepo;
import net.schwarzbaer.java.spring.sems.department.Department;
import net.schwarzbaer.java.spring.sems.department.DepartmentRepo;
import net.schwarzbaer.java.spring.sems.employee.EmployeeRepo;

@Controller
public class UserInterface {
    
	@Autowired private AddressRepo addressRepo;
	@Autowired private DepartmentRepo departmentRepo;
	@Autowired private EmployeeRepo employeeRepo;

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@GetMapping("/add_department_test")
	public @ResponseBody String addDepartmentTest(
		@RequestParam(name="name",required=true, defaultValue="unkown street") String name
	) {
		Address address = new Address("street",12,"town",12345);
		addressRepo.save(address);

		Department department = new Department(name, address);
		departmentRepo.save(department);
		return department.toString();
	}
	
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

	@GetMapping("/add_address")
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

	@GetMapping("/add_department")
	public String addDepartment(
		@RequestParam(name="name"   , required=true , defaultValue="New Department") String  name,
		@RequestParam(name="addr_id", required=false, defaultValue="-1"            ) Integer addrID
	) {
		addressRepo.findById(addrID).ifPresent(address -> {
			departmentRepo.save(new Department(name, address));
		});
		return "redirect:/edit";
	}
}
