package net.schwarzbaer.java.spring.sems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(requests -> requests
				.requestMatchers(
					UserInterface.Config.Endpoints.FullPath.ROOT,
					UserInterface.Config.Endpoints.FullPath.HOME,
					UserInterface.Config.Endpoints.FullPath.USERINFO,
					"/ldap"
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

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                    .url("ldap://localhost:8389/dc=springframework,dc=org")
                    .and()
                .passwordCompare()
                    .passwordEncoder(new BCryptPasswordEncoder())
                    .passwordAttribute("userPassword");
    }

	/*
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
	 */
}
