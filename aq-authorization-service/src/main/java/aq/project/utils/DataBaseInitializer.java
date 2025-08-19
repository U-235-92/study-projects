package aq.project.utils;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;

import aq.project.entities.Client;
import aq.project.entities.Role;
import aq.project.entities.User;
import aq.project.mappers.ClientMapper;
import aq.project.repositories.ClientRepository;
import aq.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataBaseInitializer implements ApplicationRunner {

	private final ClientMapper clientMapper;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ClientRepository clientRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		initUsers();
		initClient();
	}
	
	private void initUsers() {
		User alice = new User("alice", passwordEncoder.encode("1"), "alice@mail.aq", Role.ADMIN);
		User alexander = new User("alexander", passwordEncoder.encode("2"), "alexander@mail.aq", Role.USER);
		userRepository.save(alice);
		userRepository.save(alexander);
	}
	
	private void initClient() {
		RegisteredClient registredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("aq_client_service")
				.clientSecret(passwordEncoder.encode("secret"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.scopes(consumer -> consumer.addAll(List.of("read_msg", "write_msg", "edit_msg", "delete_msg")))
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
				.build();
		Client client = clientMapper.toClient(registredClient);
		clientRepository.save(client);
	}
}
