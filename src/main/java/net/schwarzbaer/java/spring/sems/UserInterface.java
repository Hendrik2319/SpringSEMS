package net.schwarzbaer.java.spring.sems;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
				public static final String UPDATE      = "/update";
				public static final String MODIFY      = "/modify";
			}
			public static class FullPath {
				public static final String ADDRESSES   = BASEPATH + "/addresses";
				public static final String DEPARTMENTS = BASEPATH + "/departments";
				public static final String EMPLOYEES   = BASEPATH + "/employees";
				public static final String EDIT_ALL    = BASEPATH + "";
				public static final String ROOT        = "/";
				public static final String HOME        = "/home";
				public static final String LOGIN       = "/login";
				public static final String USERINFO    = "/userinfo";
			}

			public final EntityEndpointsFullPath addresses   = new EntityEndpointsFullPath(FullPath.ADDRESSES);
			public final EntityEndpointsFullPath employees   = new EntityEndpointsFullPath(FullPath.EMPLOYEES);
			public final EntityEndpointsFullPath departments = new EntityEndpointsFullPath(FullPath.DEPARTMENTS);

			public static class EntityEndpointsFullPath {
				public final String all;
				public final String create;
				public final String update;
				public final String modify;

				private EntityEndpointsFullPath(String fullpath) {
					this.all         = fullpath + EntityCommand.ALL;
					this.create      = fullpath + EntityCommand.CREATE;
					this.update      = fullpath + EntityCommand.UPDATE;
					this.modify      = fullpath + EntityCommand.MODIFY;
				}
			}
		}

		public final Views views = new Views();
		public static class Views {
			public static final String ALL_ADDRESSES     = "all_addresses";
			public static final String ALL_DEPARTMENTS   = "all_departments";
			public static final String ALL_EMPLOYEES     = "all_employees";
			public static final String UPDATE_ADDRESS    = "update_address";
			public static final String UPDATE_DEPARTMENT = "update_department";
			public static final String UPDATE_EMPLOYEE   = "update_employee";
			public static final String EDIT              = "edit_db";
			public static final String MESSAGE           = "message";
			public static final String HOME              = "home";
			public static final String LOGIN             = "login";
			public static final String USERINFO          = "userinfo";
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
    
	@RequestMapping(Config.Endpoints.FullPath.LOGIN)
	public String showLogin(Model model) {
		return Config.Views.LOGIN;
	}
    
	@RequestMapping(Config.Endpoints.FullPath.USERINFO)
	public String showUserInfo(Model model) {
		model.addAttribute("helper"  , new UserInfoHelper());
		return Config.Views.USERINFO;
	}
	// #authorization: org.thymeleaf.extras.springsecurity6.auth.Authorization
	// #authentication: org.springframework.security.core.Authentication
	public static class UserInfoHelper {
		public String getClassStr(Object obj) {
			if (obj == null)
				return "<null>";

			String str = obj.getClass().getName();

			if (obj instanceof Iterable<?>) {
				Iterable<?> arr = (Iterable<?>)obj;
				for (Object obj2 : arr) {
					if (obj2 == null)
						str += String.format("%n-> <null>");
					else
						str += String.format("%n-> %s", obj2.getClass().getName());
				}
			}

			return str;
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
			.add(Config.Endpoints.FullPath.ROOT    , "Path Root")
			.add(Config.Endpoints.FullPath.HOME    , "Home"     )
			.add(Config.Endpoints.FullPath.LOGIN   , "Login"    )
			.add(Config.Endpoints.FullPath.USERINFO, "User Info")
			,
			new LinkList("Database Views", "with authorization")
			.add(Config.Endpoints.FullPath.ADDRESSES  , "Addresses"    )
			.add(Config.Endpoints.FullPath.DEPARTMENTS, "Departments"  )
			.add(Config.Endpoints.FullPath.EMPLOYEES  , "Employees"    )
			.add(Config.Endpoints.FullPath.EDIT_ALL   , "Edit Database")
			,
			new LinkList("REST API", "with authorization")
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
		public final String subtitle;
		public final Vector<Link> links;

		LinkList(String title) { this(title, null); }
		LinkList(String title, String subtitle) {
			this.title = title;
			this.subtitle = subtitle;
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
