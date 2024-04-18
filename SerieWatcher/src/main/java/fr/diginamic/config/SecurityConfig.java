package fr.diginamic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import fr.diginamic.models.UserAccount;
import fr.diginamic.repositories.UserAccountRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public UserDetailsService userDetailsService(
			UserAccountRepository userAccountRepository) {
		return username -> {
			UserAccount userAccount = userAccountRepository
					.findByUsername(username);
			if (userAccount == null) {
				throw new UsernameNotFoundException(
						"No user found with username: " + username);
			}
			return userAccount.asUser();
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
			throws Exception {
		http.authorizeHttpRequests(request -> request
				.requestMatchers("/login").permitAll()
				.requestMatchers("/", "/index").authenticated()
				.requestMatchers(HttpMethod.GET, "show/**", "/show/list")
				.authenticated()
				.requestMatchers(HttpMethod.POST, "show/**", "/show/add",
						"/show/delete/**")
				.hasAuthority("ROLE_ADMIN").requestMatchers("/admin")
				.hasRole("ADMIN").anyRequest().denyAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}