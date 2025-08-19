package aq.project.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import aq.project.dtos.ClientRequest;
import aq.project.dtos.ClientResponse;
import aq.project.entities.Client;
import aq.project.exceptions.ClientNameNotFoundException;
import aq.project.mappers.ClientMapper;
import aq.project.repositories.ClientRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class ClientService {

	private final ClientMapper clientMapper;
	private final PasswordEncoder passwordEncoder;
	private final ClientRepository clientRepository;
	
	public void createService(@NotNull @Valid ClientRequest clientRequest) {
		Client client = clientMapper.toClient(clientRequest);
		client.setSecret(passwordEncoder.encode(client.getSecret()));
		clientRepository.save(client);
	}
	
	public ClientResponse readClient(@NotBlank String name) {
		Client client = clientRepository.findByName(name).orElseThrow(() -> new ClientNameNotFoundException(name));
		return clientMapper.toClientResponse(client);
	}
	
	public List<ClientResponse> readClients() {
		return clientMapper.toClientResponses(clientRepository.findAll());
	}
	
	public void deleteClient(@NotBlank String name) {
		if(clientRepository.findByName(name).isEmpty())
			throw new ClientNameNotFoundException(name);
		clientRepository.deleteByName(name);
	}
}
