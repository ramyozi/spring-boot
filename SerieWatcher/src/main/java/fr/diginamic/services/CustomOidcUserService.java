package fr.diginamic.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import fr.diginamic.models.UserAccount;
import fr.diginamic.repositories.UserAccountRepository;

public class CustomOidcUserService extends OidcUserService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest)
			throws AuthenticationException {
		OidcUser oidcUser = super.loadUser(userRequest);
		try {
			return processOidcUser(oidcUser, userRequest);
		} catch (Exception e) {
			throw new AuthenticationException("Error processing OIDC user",
					e) {
			};
		}
	}

	private OidcUser processOidcUser(OidcUser oidcUser,
			OidcUserRequest userRequest) {
		String email = oidcUser.getEmail();
		UserAccount userAccount = userAccountRepository
				.findByUsername(email);
		if (userAccount == null) {
			String[] defaultRoles = { "USER" }; 
			userAccount = new UserAccount(email, "{noop}", defaultRoles);
			userAccountRepository.save(userAccount);
		}

		Set<GrantedAuthority> mappedAuthorities = userAccount.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.collect(Collectors.toSet());

		return new DefaultOidcUser(mappedAuthorities,
				oidcUser.getIdToken(), oidcUser.getUserInfo());
	}
}