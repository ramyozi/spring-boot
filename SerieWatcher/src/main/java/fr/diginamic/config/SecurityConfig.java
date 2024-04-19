package fr.diginamic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fr.diginamic.models.UserAccount;
import fr.diginamic.repositories.UserAccountRepository;
import fr.diginamic.services.CustomOidcUserService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService(
			UserAccountRepository userAccountRepository) {
		return username -> {
			UserAccount account = userAccountRepository
					.findByUsername(username);
			if (account == null) {
				throw new UsernameNotFoundException("User not found");
			}
			return account.asUser();
		};

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
			throws Exception {
		http.authorizeHttpRequests(request -> request
				.requestMatchers("/login", "/login/oauth2/code/google")
				.permitAll().requestMatchers("/", "/index").authenticated()
				.requestMatchers(HttpMethod.GET, "show/**", "/show/list")
				.authenticated()
				.requestMatchers(HttpMethod.POST, "show/**", "/show/add",
						"/show/delete/**")
				.hasAuthority("ROLE_ADMIN").requestMatchers("/admin")
				.hasRole("ADMIN").anyRequest().denyAll())
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
				.oauth2Login(Customizer.withDefaults());
		return http.build();
	}

	private CustomOidcUserService oidcUserService() {
		return new CustomOidcUserService();
	}

}