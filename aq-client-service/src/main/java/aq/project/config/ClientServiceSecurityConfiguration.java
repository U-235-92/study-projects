package aq.project.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ClientServiceSecurityConfiguration {

	@Value("${aq.client-service.token-uri}")
	private String tokenUri;
	@Value("${aq.client-service.client-id}")
	private String clientId;
	@Value("${aq.client-service.client-secret}")
	private String clientSecret;
	
	@Bean
	SecurityFilterChain clientServiceFilterChain(HttpSecurity http) throws Exception {
		http.oauth2Client(Customizer.withDefaults());
		
		http.authorizeHttpRequests(cust -> cust.anyRequest().permitAll());
		
		http.csrf(cust -> cust.disable());
		
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
	}
	
	@Bean
	ClientRegistrationRepository clientRegistrationRepository() {
		ClientRegistration client = ClientRegistration.withRegistrationId(clientId)
				.tokenUri(tokenUri)
				.clientId(clientId)
				.clientSecret(passwordEncoder().encode(clientSecret))
				.scope(List.of("read_msg", "write_msg", "edit_msg", "delete_msg"))
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.build();
		ClientRegistrationRepository repository = new InMemoryClientRegistrationRepository(client);
		return repository;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository) {
		OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
		DefaultOAuth2AuthorizedClientManager manager = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository(), auth2AuthorizedClientRepository);
		manager.setAuthorizedClientProvider(provider);
		return manager;
	}
}
