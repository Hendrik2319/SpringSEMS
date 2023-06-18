package net.schwarzbaer.java.spring.sems.ldap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LdapInspectController {
    
	@GetMapping("/ldap")
	public String showLdapView(Model model) {
		model.addAttribute("data", new String[] {});
		return "ldap_view";
	}
}
