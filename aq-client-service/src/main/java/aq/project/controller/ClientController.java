package aq.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dto.MessageResponse;
import aq.project.service.ClientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

	private final ClientService clientService;
	
	@GetMapping("/read-message/{id}")
	public MessageResponse readMessage(@PathVariable(required = true) int id) {
		return clientService.readMessage(id);
	}
}
