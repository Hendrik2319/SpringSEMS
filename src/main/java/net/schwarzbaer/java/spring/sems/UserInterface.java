package net.schwarzbaer.java.spring.sems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.schwarzbaer.java.spring.sems.address.Address;
import net.schwarzbaer.java.spring.sems.address.AddressRepo;
import net.schwarzbaer.java.spring.sems.department.Department;
import net.schwarzbaer.java.spring.sems.department.DepartmentRepo;
import net.schwarzbaer.java.spring.sems.employee.Employee;
import net.schwarzbaer.java.spring.sems.employee.EmployeeRepo;

@Controller
@RequestMapping("/forms")
public class UserInterface {
    
	@Autowired private AddressRepo addressRepo;
	@Autowired private DepartmentRepo departmentRepo;
	@Autowired private EmployeeRepo employeeRepo;

	public static final Config config = new Config();
	public static class Config {

		public static final String basePath = "/forms";

		public static final Endpoints endpoints = new Endpoints();
		public static class Endpoints {
			public static final String all_addresses     = "/all_addresses";
			public static final String all_departments   = "/all_departments";
			public static final String all_employees     = "/all_employees";
			public static final String edit              = "/edit";
			public static final String add_address       = "/add_address";
			public static final String add_department    = "/add_department";
			public static final String add_employee      = "/add_employee";
			public static final String delete_address    = "/delete_address";
			public static final String delete_employee   = "/delete_employee";
			public static final String delete_department = "/delete_department";
			public static final String update_address    = "/update_address";
			public static final String update_department = "/update_department";
			public static final String update_employee   = "/update_employee";
			public static final String call_update_address_view    = "/call_update_address_view";
			public static final String call_update_department_view = "/call_update_department_view";
			public static final String call_update_employee_view   = "/call_update_employee_view";
		}

		public static final Views views = new Views();
		public static class Views {
			public static final String all_addresses   = "all_addresses";
			public static final String all_departments = "all_departments";
			public static final String all_employees   = "all_employees";
			public static final String edit            = "edit_db_view";
			public static final String message         = "message";
			public static final String update_address    = "update_address";
			public static final String update_department = "update_department";
			public static final String update_employee   = "update_employee";
		}
	}

	private String buildRedirectStatement(String redirectTarget) {
		return "redirect:"+redirectTarget;
	}

	private String showMessage(Model model, String  redirectTarget, String title, String... paragraphs) {
		model.addAttribute("redirectTarget", redirectTarget);
		model.addAttribute("title", title);
		model.addAttribute("paragraphs", paragraphs);
		return Config.Views.message;
	}

	@GetMapping(Config.Endpoints.all_addresses)
	public String showAllAddresses(Model model) {
		model.addAttribute("allAddresses", addressRepo.findAll());
		model.addAttribute("redirectTarget", Config.basePath + Config.Endpoints.all_addresses);
		model.addAttribute("config", config);
		return Config.Views.all_addresses;
	}
    
	@GetMapping(Config.Endpoints.all_departments)
	public String showAllDepartments(Model model) {
		model.addAttribute("allDepartments", departmentRepo.findAll());
		model.addAttribute("redirectTarget", Config.basePath + Config.Endpoints.all_departments);
		model.addAttribute("config", config);
		return Config.Views.all_departments;
	}
    
	@GetMapping(Config.Endpoints.all_employees)
	public String showAllEmployees(Model model) {
		model.addAttribute("allEmployees", employeeRepo.findAll());
		model.addAttribute("redirectTarget", Config.basePath + Config.Endpoints.all_employees);
		model.addAttribute("config", config);
		return Config.Views.all_employees;
	}
    
	@GetMapping(Config.Endpoints.edit)
	public String showAddDepartmentView(Model model) {
		model.addAttribute("allAddresses",   addressRepo   .findAll());
		model.addAttribute("allDepartments", departmentRepo.findAll());
		model.addAttribute("allEmployees",   employeeRepo  .findAll());
		model.addAttribute("redirectTarget", Config.basePath + Config.Endpoints.edit);
		model.addAttribute("config", config);
		return Config.Views.edit;
	}

	@PostMapping(Config.Endpoints.add_address)
	public String addAddress(
		@RequestParam(name="street"     , defaultValue="unkown street") String  street,
		@RequestParam(name="house"      , defaultValue="12"           ) Integer housenumber,
		@RequestParam(name="town"       , defaultValue="unkown town"  ) String  town,
		@RequestParam(name="zipcode"    , defaultValue="123"          ) Integer zipcode,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget
	) {
		Address address = new Address(street,housenumber,town,zipcode);
		addressRepo.save(address);
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(Config.Endpoints.add_department)
	public String addDepartment(
		@RequestParam(name="name"       , defaultValue="New Department") String  name,
		@RequestParam(name="addr_id"    , defaultValue="-1"            ) Integer addrID,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget
	) {
		Address address = addressRepo.findById(addrID).orElse(null);
		departmentRepo.save(new Department(name, address));
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(Config.Endpoints.add_employee)
	public String addEmployee(
		@RequestParam(name="forename"   , defaultValue="<forename>") String  forename,
		@RequestParam(name="surname"    , defaultValue="<surname>" ) String  surname,
		@RequestParam(name="addr_id"    , defaultValue="-1"       ) Integer addrID,
		@RequestParam(name="dep_id"     , defaultValue="-1"       ) Integer depID,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget
	) {
		Address    address    =    addressRepo.findById(addrID).orElse(null);
		Department department = departmentRepo.findById( depID).orElse(null);
		employeeRepo.save(new Employee(forename, surname, address, department));
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(Config.Endpoints.delete_address)
	public String deleteAddress(
		@RequestParam(name="id"         , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget,
		Model model
	) {
		if (addressRepo.existsById(id)) {
			int departmentsUsingCount = departmentRepo.countByAddressID(id);
			int employeesUsingCount   = employeeRepo  .countByAddressID(id);
			//System.out.printf("Address[ID:%d] is assigned to %d departments and %d employees.%n", id, departmentsUsingCount, employeesUsingCount);

			if (departmentsUsingCount!=0 || employeesUsingCount!=0) {
				String assignmentsMsg = "{assignmentsMsg}";
				String relevantGroups = "{relevantGroups}";
				if (departmentsUsingCount!=0 && employeesUsingCount!=0) {
					assignmentsMsg = String.format("Address is assigned to %d department(s) and %d employee(s).", departmentsUsingCount, employeesUsingCount);
					relevantGroups = "department(s) and employee(s)";
				} else if (departmentsUsingCount!=0) {
					assignmentsMsg = String.format("Address is assigned to %d department(s).", departmentsUsingCount);
					relevantGroups = "department(s)";
				} else if (employeesUsingCount!=0) {
					assignmentsMsg = String.format("Address is assigned to %d employee(s).", employeesUsingCount);
					relevantGroups = "employee(s)";
				}
				return showMessage(model, redirectTarget,
					"Can't delete address.",
					assignmentsMsg,
					String.format("You should assign different addresses to the relevant %s.", relevantGroups)
				);
			}

			addressRepo.deleteById(id);
		}
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(Config.Endpoints.delete_department)
	public String deleteDepartment(
		@RequestParam(name="id"         , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget,
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

	@PostMapping(Config.Endpoints.delete_employee)
	public String deleteEmployee(
		@RequestParam(name="id"         , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget
	) {
		employeeRepo.deleteById(id);
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(Config.Endpoints.call_update_address_view)
	public String callUpdateAddressView(
		@RequestParam(name="id"         , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget,
		Model model
	) {
		Address address = addressRepo.findById(id).orElse(null);
		if (address==null) return buildRedirectStatement(redirectTarget);
		model.addAttribute("address"       , address);
		model.addAttribute("redirectTarget", redirectTarget);
		model.addAttribute("config"        , config);
		return Config.Views.update_address;
	}

	@PostMapping(Config.Endpoints.call_update_department_view)
	public String callUpdateDepartmentView(
		@RequestParam(name="id"         , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget,
		Model model
	) {
		Department department = departmentRepo.findById(id).orElse(null);
		if (department==null) return buildRedirectStatement(redirectTarget);
		model.addAttribute("department"    , department);
		model.addAttribute("allAddresses"  , addressRepo.findAll());
		model.addAttribute("redirectTarget", redirectTarget);
		model.addAttribute("config"        , config);
		return Config.Views.update_department;
	}

	@PostMapping(Config.Endpoints.call_update_employee_view)
	public String callUpdateEmployeeView(
		@RequestParam(name="id"         , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget,
		Model model
	) {
		Employee employee = employeeRepo.findById(id).orElse(null);
		if (employee==null) return buildRedirectStatement(redirectTarget);
		model.addAttribute("employee"      , employee);
		model.addAttribute("allAddresses"  , addressRepo   .findAll());
		model.addAttribute("allDepartments", departmentRepo.findAll());
		model.addAttribute("redirectTarget", redirectTarget);
		model.addAttribute("config"        , config);
		return Config.Views.update_employee;
	}

	@PostMapping(Config.Endpoints.update_address)
	public String updateAddress(
		@RequestParam(name="id"         , defaultValue="-1"      ) Integer id,
		@RequestParam(name="street"     , defaultValue="<street>") String  street,
		@RequestParam(name="house"      , defaultValue="12"      ) Integer housenumber,
		@RequestParam(name="town"       , defaultValue="<town>"  ) String  town,
		@RequestParam(name="zipcode"    , defaultValue="123"     ) Integer zipcode,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget
	) {
		addressRepo.findById(id).ifPresent(address -> {
			address.setStreet     (street     );
			address.setHousenumber(housenumber);
			address.setTown       (town       );
			address.setZipcode    (zipcode    );
			addressRepo.save(address);
		});
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(Config.Endpoints.update_department)
	public String updateDepartment(
		@RequestParam(name="id"         , defaultValue="-1"          ) Integer id,
		@RequestParam(name="name"       , defaultValue="<department>") String  name,
		@RequestParam(name="addr_id"    , defaultValue="-1"          ) Integer addrID,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget
	) {
		departmentRepo.findById(id).ifPresent(department -> {
			department.setName   (name);
			department.setAddress(addressRepo.findById(addrID).orElse(null));
			departmentRepo.save(department);
		});
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(Config.Endpoints.update_employee)
	public String updateEmployee(
		@RequestParam(name="id"         , defaultValue="-1"        ) Integer id,
		@RequestParam(name="forename"   , defaultValue="<forename>") String  forename,
		@RequestParam(name="surname"    , defaultValue="<surname>" ) String  surname,
		@RequestParam(name="addr_id"    , defaultValue="-1"        ) Integer addrID,
		@RequestParam(name="dep_id"     , defaultValue="-1"        ) Integer depID,
		@RequestParam(name="redirect_to", defaultValue=Config.basePath + Config.Endpoints.edit) String  redirectTarget
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
