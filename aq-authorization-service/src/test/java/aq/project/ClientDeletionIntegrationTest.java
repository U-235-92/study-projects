package aq.project;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import aq.project.entities.Client;
import aq.project.exceptions.ClientNameNotFoundException;
import aq.project.repositories.ClientRepository;
import aq.project.services.ClientService;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class ClientDeletionIntegrationTest {

	@Autowired
	private ClientService clientService;
	@Autowired
	private ClientRepository clientRepository;
	
	@Test
	@DisplayName("successDeleteOfClientTest")
	void test1() {
		Client client = new Client("delete-service", "secret", List.of("delete"), ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue(), AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
		clientRepository.save(client);
		clientService.deleteClient("delete-service");
		Assertions.assertThrows(ClientNameNotFoundException.class, () -> clientService.readClient("delete-service"));
	}
	
	@Test
	@DisplayName("faliDeleteOfUnknownClientTest")
	void test2() {
		Assertions.assertThrows(ClientNameNotFoundException.class, () -> clientService.deleteClient("unknown-service"));
	}
	
	@Test
	@DisplayName("faliDeletionWithNullParameterTest")
	void test3() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> clientService.deleteClient(null));
	}
	
	@Test
	@DisplayName("faliDeletionWithBlankParameterTest")
	void test4() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> clientService.deleteClient(""));
	}
}
