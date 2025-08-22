package aq.project;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import aq.project.dtos.ClientRequest;
import aq.project.entities.Client;
import aq.project.repositories.ClientRepository;
import aq.project.services.ClientService;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class ClientCreationIntegrationTest {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired @InjectMocks
	private ClientService clientService;
	
	@Test
	@DisplayName("successClientCreationTest")
	void test1() {
		ClientRequest clientRequest = new ClientRequest("acme-service", "secret", List.of("fun", "run"), ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue(), AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
		clientService.createService(clientRequest);
		Client dbClient = clientRepository.findByName("acme-service").get();
		Assertions.assertEquals(clientRequest.getName(), dbClient.getName());
		Assertions.assertNotNull(dbClient.getId());
	}
	
	@Test
	@DisplayName("failClientCreationWithNoValidParametersTest")
	void test2() {
		ClientRequest clientRequest = new ClientRequest("", null, List.of("fun", "run"), ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue(), AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
		Assertions.assertThrows(ConstraintViolationException.class, () -> clientService.createService(clientRequest));
	}
	
	@Test
	@DisplayName("failClientCreationWithNullParameterTest")
	void test3() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> clientService.createService(null));
	}
}
