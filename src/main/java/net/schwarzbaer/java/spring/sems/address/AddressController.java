package net.schwarzbaer.java.spring.sems.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.schwarzbaer.java.spring.sems.UserInterface;
import net.schwarzbaer.java.spring.sems.UserInterface.Config;
import net.schwarzbaer.java.spring.sems.department.DepartmentRepo;
import net.schwarzbaer.java.spring.sems.employee.EmployeeRepo;

@Controller
@RequestMapping(Config.Endpoints.FullPath.ADDRESSES)
public class AddressController extends UserInterface.Helper {
    
	@Autowired private AddressRepo addressRepo;
	@Autowired private DepartmentRepo departmentRepo;
	@Autowired private EmployeeRepo employeeRepo;

	@GetMapping(Config.Endpoints.EntityCommand.ALL)
	public String showAll(Model model) {
		model.addAttribute("allAddresses"  , addressRepo.findAll());
		model.addAttribute("redirectTarget", config.endpoints.addresses.all);
		model.addAttribute("config"        , config);
		return Config.Views.ALL_ADDRESSES;
	}

	@PostMapping(Config.Endpoints.EntityCommand.CREATE)
	public String add(
		@RequestParam(name="street"     , defaultValue="<street>") String  street,
		@RequestParam(name="house"      , defaultValue="12"      ) Integer housenumber,
		@RequestParam(name="town"       , defaultValue="<town>"  ) String  town,
		@RequestParam(name="zipcode"    , defaultValue="12345"   ) Integer zipcode,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget
	) {
		Address address = new Address(street,housenumber,town,zipcode);
		addressRepo.save(address);
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(path = Config.Endpoints.EntityCommand.MODIFY, params = "delete")
	public String delete(
		@RequestParam(name="delete"     , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget,
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
				return showMessage(model, redirectTarget, Config.MessageType.WARNING,
					"Can't delete address.",
					assignmentsMsg,
					String.format("You should assign different addresses to the relevant %s.", relevantGroups)
				);
			}

			addressRepo.deleteById(id);
		}
		return buildRedirectStatement(redirectTarget);
	}

	@PostMapping(path = Config.Endpoints.EntityCommand.MODIFY, params = "update")
	public String callUpdateView(
		@RequestParam(name="update"     , defaultValue="-1") Integer id,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget,
		Model model
	) {
		Address address = addressRepo.findById(id).orElse(null);
		if (address==null) return buildRedirectStatement(redirectTarget);
		model.addAttribute("address"       , address);
		model.addAttribute("redirectTarget", redirectTarget);
		model.addAttribute("config"        , config);
		return Config.Views.UPDATE_ADDRESS;
	}

	@PostMapping(Config.Endpoints.EntityCommand.UPDATE)
	public String update(
		@RequestParam(name="id"         , defaultValue="-1"      ) Integer id,
		@RequestParam(name="street"     , defaultValue="<street>") String  street,
		@RequestParam(name="house"      , defaultValue="12"      ) Integer housenumber,
		@RequestParam(name="town"       , defaultValue="<town>"  ) String  town,
		@RequestParam(name="zipcode"    , defaultValue="12345"   ) Integer zipcode,
		@RequestParam(name="redirect_to", defaultValue=Config.Endpoints.FullPath.EDIT_ALL) String  redirectTarget
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
}
