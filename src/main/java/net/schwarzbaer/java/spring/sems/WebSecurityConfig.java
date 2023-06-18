package net.schwarzbaer.java.spring.sems;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(requests -> requests
				.requestMatchers(
					UserInterface.Config.Endpoints.FullPath.ROOT,
					UserInterface.Config.Endpoints.FullPath.HOME,
					UserInterface.Config.Endpoints.FullPath.USERINFO
				).permitAll() // landing page and others
				.requestMatchers("/stylesheets/**", "/js/**").permitAll() // web resources
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage(UserInterface.Config.Endpoints.FullPath.LOGIN)
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}    
}
