package aq.project.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dtos.ClientRequest;
import aq.project.dtos.ClientResponse;
import aq.project.services.ClientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

	private final ClientService clientService;
	
	@ResponseBody
	@PostMapping("/create")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String createService(@RequestBody(required = true) ClientRequest clientRequest) {
		clientService.createService(clientRequest);
		return String.format("Client with name %s was created", clientRequest.getName());
	}
	
	@ResponseBody
	@GetMapping("/get/{name}")
	@ResponseStatus(code = HttpStatus.OK)
	public ClientResponse getClient(@PathVariable(required = true) String name) {
		return clientService.readClient(name);
	}
	
	@ResponseBody
	@GetMapping("/get-all")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ClientResponse> getClients() {
		return clientService.readClients();
	}
	
	@ResponseBody
	@DeleteMapping("/delete/{name}")
	@ResponseStatus(code = HttpStatus.OK)
	public String deleteClient(@PathVariable(required = true) String name) {
		clientService.deleteClient(name);
		return String.format("Client with name %s was deleted", name);
	}
}
