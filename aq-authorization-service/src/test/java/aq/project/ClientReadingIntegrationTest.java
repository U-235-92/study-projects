package aq.project;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import aq.project.dtos.ClientResponse;
import aq.project.entities.Client;
import aq.project.exceptions.ClientNameNotFoundException;
import aq.project.repositories.ClientRepository;
import aq.project.services.ClientService;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class ClientReadingIntegrationTest {

	@Autowired
	private ClientService clientService;
	@MockitoBean
	private ClientRepository clientRepository;
	
	@Test
	@DisplayName("successReadingClientTest")
	void test1() {
		Client client = new Client("acme-service", "secret", List.of("fun", "run"), ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue(), AuthorizationGrantType.CLIENT_CREDENTIALS.getValue());
		Mockito.when(clientRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(client));
		ClientResponse clientResponse = clientService.readClient("acme-service");
		Assertions.assertNotNull(clientResponse);
	}
	
	@Test
	@DisplayName("failReadingClientTestWithNullParameter")
	void test2() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> clientService.readClient(null));
	}
	
	@Test
	@DisplayName("failReadingClientTestWithBlankParameter")
	void test3() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> clientService.readClient(""));
	}
	
	@Test
	@DisplayName("failReadingClientTestWithUnknownClient")
	void test4() {
		Assertions.assertThrows(ClientNameNotFoundException.class, () -> clientService.readClient("unknown-service"));
	}
}
