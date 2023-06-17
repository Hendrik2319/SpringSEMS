package net.schwarzbaer.java.spring.sems;

import java.util.Vector;

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
				public static final String CREATE      = "/create";
				public static final String DELETE      = "/delete";
				public static final String UPDATE      = "/update";
				public static final String UPDATE_VIEW = "/update_view";
			}
			public static class FullPath {
				public static final String ADDRESSES   = BASEPATH + "/addresses";
				public static final String DEPARTMENTS = BASEPATH + "/departments";
				public static final String EMPLOYEES   = BASEPATH + "/employees";
				public static final String EDIT_ALL    = BASEPATH + "";
				public static final String ROOT        = "/";
				public static final String HOME        = "/home";
			}

			public final EntityEndpointsFullPath addresses   = new EntityEndpointsFullPath(FullPath.ADDRESSES);
			public final EntityEndpointsFullPath employees   = new EntityEndpointsFullPath(FullPath.EMPLOYEES);
			public final EntityEndpointsFullPath departments = new EntityEndpointsFullPath(FullPath.DEPARTMENTS);

			public static class EntityEndpointsFullPath {
				public final String all;
				public final String create;
				public final String delete;
				public final String update;
				public final String update_view;

				private EntityEndpointsFullPath(String fullpath) {
					this.all         = fullpath + EntityCommand.ALL;
					this.create      = fullpath + EntityCommand.CREATE;
					this.delete      = fullpath + EntityCommand.DELETE;
					this.update      = fullpath + EntityCommand.UPDATE;
					this.update_view = fullpath + EntityCommand.UPDATE_VIEW;
				}
			}
		}

		public final Views views = new Views();
		public static class Views {
			public static final String ALL_ADDRESSES   = "all_addresses";
			public static final String ALL_DEPARTMENTS = "all_departments";
			public static final String ALL_EMPLOYEES   = "all_employees";
			public static final String EDIT            = "edit_db";
			public static final String MESSAGE         = "message";
			public static final String UPDATE_ADDRESS    = "update_address";
			public static final String UPDATE_DEPARTMENT = "update_department";
			public static final String UPDATE_EMPLOYEE   = "update_employee";
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
			return Config.Views.MESSAGE;
		}
	}
    
	@GetMapping(Config.Endpoints.FullPath.EDIT_ALL)
	public String showEditView(Model model) {
		model.addAttribute("allAddresses"  , addressRepo   .findAll());
		model.addAttribute("allDepartments", departmentRepo.findAll());
		model.addAttribute("allEmployees"  , employeeRepo  .findAll());
		model.addAttribute("redirectTarget", Config.Endpoints.FullPath.EDIT_ALL);
		model.addAttribute("config"        , Helper.config);
		return Config.Views.EDIT;
	}
    
	@GetMapping({Config.Endpoints.FullPath.ROOT, Config.Endpoints.FullPath.HOME})
	public String showHome(Model model) {
		model.addAttribute("linklists", new LinkList[] {
			new LinkList("General")
			.add(Config.Endpoints.FullPath.ROOT, "Path Root")
			.add(Config.Endpoints.FullPath.HOME, "Home")
			,
			new LinkList("Database Views")
			.add(Config.Endpoints.FullPath.ADDRESSES  , "Addresses")
			.add(Config.Endpoints.FullPath.DEPARTMENTS, "Departments")
			.add(Config.Endpoints.FullPath.EMPLOYEES  , "Employees")
			.add(Config.Endpoints.FullPath.EDIT_ALL   , "Edit Database")
			,
			new LinkList("REST API")
			.add("/rest"              )
			.add("/rest/addresses"    )
			.add("/rest/addresses/1"  )
			.add("/rest/departments"  )
			.add("/rest/departments/1")
			.add("/rest/employees"    )
			.add("/rest/employees/1"  )
		});
		return "home";
	}

	public static class LinkList
	{
		public final String title;
		public final Vector<Link> links;

		LinkList(String title) {
			this.title = title;
			links = new Vector<>();
		}
		LinkList add(String path              ) { links.add(new Link(path       )); return this; }
		LinkList add(String path, String label) { links.add(new Link(path, label)); return this; }
	}

	public static class Link
	{
		public final String path;
		public final String label;

		private Link(String path) { this(path, path); }
		private Link(String path, String label)
		{
			this.path = path;
			this.label = label;
		}
	}

}
