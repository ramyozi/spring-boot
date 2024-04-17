package fr.diginamic.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

import fr.diginamic.models.UserAccount;
import fr.diginamic.repositories.UserAccountRepository;

@Configuration
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
	SecurityFilterChain configSecurity(HttpSecurity httpSecurity)
			throws Exception {
		httpSecurity
				.authorizeHttpRequests(request -> request
						.requestMatchers("/login").permitAll()
						.requestMatchers("/", "/index").authenticated()
						.requestMatchers(HttpMethod.GET, "city/**",
								"/city/list")
						.authenticated()
						.requestMatchers(HttpMethod.POST, "city/**")
						.hasAuthority("ROLE_ADMIN")
						.requestMatchers("/admin").hasRole("ADMIN")
						.anyRequest().denyAll())
				.httpBasic(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults());
		return httpSecurity.build();
	}
}
