package aq.project.security.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import aq.project.entities.Client;
import aq.project.exceptions.ClientIdNotFoundException;
import aq.project.exceptions.ClientNameNotFoundException;
import aq.project.mappers.ClientMapper;
import aq.project.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaRegistredClientService implements RegisteredClientRepository {

	private final ClientMapper clientMapper;
	private final PasswordEncoder passwordEncoder;
	private final ClientRepository clientRepository;
	
	@Override
	public void save(RegisteredClient registeredClient) {
		Client client = clientMapper.toClient(registeredClient);
		client.setSecret(passwordEncoder.encode(client.getSecret()));
		clientRepository.save(client);
	}

	@Override
	public RegisteredClient findById(String id) {
		Client client = clientRepository.findById(id).orElseThrow(() -> new ClientIdNotFoundException(id));
		return clientMapper.toRegistredClient(client);
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		Client client = clientRepository.findByName(clientId).orElseThrow(() -> new ClientNameNotFoundException(clientId));
		return clientMapper.toRegistredClient(client);
	}
}
