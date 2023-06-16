package net.schwarzbaer.java.spring.sems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.schwarzbaer.java.spring.sems.address.AddressRepo;
import net.schwarzbaer.java.spring.sems.department.DepartmentRepo;
import net.schwarzbaer.java.spring.sems.employee.EmployeeRepo;

@Controller
public class UserInterface {
    
	@Autowired private AddressRepo addressRepo;
	@Autowired private DepartmentRepo departmentRepo;
	@Autowired private EmployeeRepo employeeRepo;

	public static class Config {

		public static final String BASEPATH = "/forms";
		
		public final Endpoints endpoints = new Endpoints();
		public static class Endpoints {
			public static class EntityCommand {
				public static final String ALL         = "";
				public static final String ADD         = "/add";
				public static final String DELETE      = "/delete";
				public static final String UPDATE      = "/update";
				public static final String UPDATE_VIEW = "/update_view";
			}
			public static class FullPath {
				public static final String ADDRESSES   = BASEPATH + "/addresses";
				public static final String EMPLOYEES   = BASEPATH + "/employees";
				public static final String DEPARTMENTS = BASEPATH + "/departments";
				public static final String EDIT        = BASEPATH + "/edit";
			}

			public final EntityEndpointsFullPath addresses   = new EntityEndpointsFullPath(FullPath.ADDRESSES);
			public final EntityEndpointsFullPath employees   = new EntityEndpointsFullPath(FullPath.EMPLOYEES);
			public final EntityEndpointsFullPath departments = new EntityEndpointsFullPath(FullPath.DEPARTMENTS);

			public static class EntityEndpointsFullPath {
				public final String all;
				public final String add;
				public final String delete;
				public final String update;
				public final String update_view;

				private EntityEndpointsFullPath(String fullpath) {
					this.all         = fullpath + EntityCommand.ALL;
					this.add         = fullpath + EntityCommand.ADD;
					this.delete      = fullpath + EntityCommand.DELETE;
					this.update      = fullpath + EntityCommand.UPDATE;
					this.update_view = fullpath + EntityCommand.UPDATE_VIEW;
				}
			}
		}

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

	public static class Helper {

		public static final Config config = new Config();

		public static String buildRedirectStatement(String redirectTarget) {
			return "redirect:"+redirectTarget;
		}

		public static String showMessage(Model model, String  redirectTarget, String title, String... paragraphs) {
			model.addAttribute("redirectTarget", redirectTarget);
			model.addAttribute("title", title);
			model.addAttribute("paragraphs", paragraphs);
			return Config.Views.message;
		}
	}
    
	@GetMapping(Config.Endpoints.FullPath.EDIT)
	public String showEditView(Model model) {
		model.addAttribute("allAddresses",   addressRepo   .findAll());
		model.addAttribute("allDepartments", departmentRepo.findAll());
		model.addAttribute("allEmployees",   employeeRepo  .findAll());
		model.addAttribute("redirectTarget", Config.Endpoints.FullPath.EDIT);
		model.addAttribute("config", Helper.config);
		return Config.Views.edit;
	}
}
