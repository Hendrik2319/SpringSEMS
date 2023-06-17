package net.schwarzbaer.java.spring.sems.department;

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
import net.schwarzbaer.java.spring.sems.employee.EmployeeRepo;

@Controller
@RequestMapping(Config.Endpoints.FullPath.DEPARTMENTS)
public class DepartmentController extends UserInterface.Helper {
    
	@Autowired private AddressRepo addressRepo;
	@Autowired private DepartmentRepo departmentRepo;
	@Autowired private EmployeeRepo employeeRepo;
    
	@GetMapping(Config.Endpoints.EntityCommand.ALL)
	public String showAll(Model model) {
		model.addAttribute("allAddresses"  , addressRepo   .findAll());
		model.addAttribute("allDepartments", departmentRepo.findAll());
		model.addAttribute("redirectTarget", config.endpoints.departments.all);
		model.addAttribute("config"        , config);
		return Config.Views.ALL_DEPARTMENTS;
	}

	@PostMapping(Config.Endpoints.EntityCommand.CREATE)
	public String add(
		@RequestParam(name="name"       , defaultValue="<department>") String  name,
		@RequestParam(name="addr_id"    , defaultValue="-1"          ) Integer addrID,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget
	) {
		Address address = addressRepo.findById(addrID).orElse(null);
		departmentRepo.save(new Department(name, address));
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(path = Config.Endpoints.EntityCommand.MODIFY, params = "delete")
	public String delete(
		@RequestParam(name="delete"     , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget,
		Model model
	) {
		if (departmentRepo.existsById(id)) {
			int employeesUsingCount = employeeRepo.countByDepartmentID(id);
			//System.out.printf("Department[ID:%d]* is assigned to %d employees.%n", id, employeesUsingCount);

			if (employeesUsingCount!=0) {
				return showMessage(model, redirectTarget,
					"Can't delete department.",
					String.format("Department is assigned to %d employee(s).", employeesUsingCount),
					"You should assign different departments to the relevant employee(s)."
				);
			}

			if (employeesUsingCount==0)
				departmentRepo.deleteById(id);
		}
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(path = Config.Endpoints.EntityCommand.MODIFY, params = "update")
	public String callUpdateView(
		@RequestParam(name="update"     , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget,
		Model model
	) {
		Department department = departmentRepo.findById(id).orElse(null);
		if (department==null) return buildRedirectStatement(redirectTarget);
		model.addAttribute("department"    , department);
		model.addAttribute("allAddresses"  , addressRepo.findAll());
		model.addAttribute("redirectTarget", redirectTarget);
		model.addAttribute("config"        , config);
		return Config.Views.UPDATE_DEPARTMENT;
	}

	@PostMapping(Config.Endpoints.EntityCommand.UPDATE)
	public String update(
		@RequestParam(name="id"         , defaultValue="-1"          ) Integer id,
		@RequestParam(name="name"       , defaultValue="<department>") String  name,
		@RequestParam(name="addr_id"    , defaultValue="-1"          ) Integer addrID,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget
	) {
		departmentRepo.findById(id).ifPresent(department -> {
			department.setName   (name);
			department.setAddress(addressRepo.findById(addrID).orElse(null));
			departmentRepo.save(department);
		});
		return buildRedirectStatement(redirectTarget);
	}
}
