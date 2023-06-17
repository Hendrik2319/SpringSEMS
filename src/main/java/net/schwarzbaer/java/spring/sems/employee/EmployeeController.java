package net.schwarzbaer.java.spring.sems.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.schwarzbaer.java.spring.sems.UserInterface;
import net.schwarzbaer.java.spring.sems.UserInterface.Config;
import net.schwarzbaer.java.spring.sems.address.Address;
import net.schwarzbaer.java.spring.sems.address.AddressRepo;
import net.schwarzbaer.java.spring.sems.department.Department;
import net.schwarzbaer.java.spring.sems.department.DepartmentRepo;

@Controller
@RequestMapping(Config.Endpoints.FullPath.EMPLOYEES)
public class EmployeeController extends UserInterface.Helper {
    
	@Autowired private AddressRepo addressRepo;
	@Autowired private DepartmentRepo departmentRepo;
	@Autowired private EmployeeRepo employeeRepo;
    
	@GetMapping(Config.Endpoints.EntityCommand.ALL)
	public String showAll(Model model) {
		model.addAttribute("allAddresses"  , addressRepo   .findAll());
		model.addAttribute("allDepartments", departmentRepo.findAll());
		model.addAttribute("allEmployees"  , employeeRepo  .findAll());
		model.addAttribute("redirectTarget", config.endpoints.employees.all);
		model.addAttribute("config"        , config);
		return Config.Views.ALL_EMPLOYEES;
	}

	@PostMapping(Config.Endpoints.EntityCommand.CREATE)
	public String add(
		@RequestParam(name="forename"   , defaultValue="<forename>") String  forename,
		@RequestParam(name="surname"    , defaultValue="<surname>" ) String  surname,
		@RequestParam(name="addr_id"    , defaultValue="-1"       ) Integer addrID,
		@RequestParam(name="dep_id"     , defaultValue="-1"       ) Integer depID,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget
	) {
		Address    address    =    addressRepo.findById(addrID).orElse(null);
		Department department = departmentRepo.findById( depID).orElse(null);
		employeeRepo.save(new Employee(forename, surname, address, department));
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(Config.Endpoints.EntityCommand.DELETE)
	public String delete(
		@RequestParam(name="id"         , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget
	) {
		employeeRepo.deleteById(id);
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(Config.Endpoints.EntityCommand.UPDATE_VIEW)
	public String callUpdateView(
		@RequestParam(name="id"         , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget,
		Model model
	) {
		Employee employee = employeeRepo.findById(id).orElse(null);
		if (employee==null) return buildRedirectStatement(redirectTarget);
		model.addAttribute("employee"      , employee);
		model.addAttribute("allAddresses"  , addressRepo   .findAll());
		model.addAttribute("allDepartments", departmentRepo.findAll());
		model.addAttribute("redirectTarget", redirectTarget);
		model.addAttribute("config"        , config);
		return Config.Views.UPDATE_EMPLOYEE;
	}

	@PostMapping(Config.Endpoints.EntityCommand.UPDATE)
	public String update(
		@RequestParam(name="id"         , defaultValue="-1"        ) Integer id,
		@RequestParam(name="forename"   , defaultValue="<forename>") String  forename,
		@RequestParam(name="surname"    , defaultValue="<surname>" ) String  surname,
		@RequestParam(name="addr_id"    , defaultValue="-1"        ) Integer addrID,
		@RequestParam(name="dep_id"     , defaultValue="-1"        ) Integer depID,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget
	) {
		employeeRepo.findById(id).ifPresent(employee -> {
			employee.setForename  (forename);
			employee.setSurname   (surname );
			employee.setAddress   (   addressRepo.findById(addrID).orElse(null));
			employee.setDepartment(departmentRepo.findById( depID).orElse(null));
			employeeRepo.save(employee);
		});
		return buildRedirectStatement(redirectTarget);
	}
}
