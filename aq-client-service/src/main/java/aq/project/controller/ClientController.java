package aq.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dto.MessageRequest;
import aq.project.dto.MessageResponse;
import aq.project.service.ClientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

	private final ClientService clientService;
	
	@ResponseBody
	@GetMapping("/read-message/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public MessageResponse readMessage(@PathVariable(required = true) int id) {
		return clientService.readMessage(id);
	}
	
	@ResponseBody
	@PostMapping("/write-message")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String writeMessage(@RequestBody(required = true) MessageRequest messageRequest) {
		return clientService.writeMessage(messageRequest);
	}
	
	@ResponseBody
	@PatchMapping("/edit-message/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public String editMessage(@PathVariable(required = true) int id, @RequestBody(required = true) String text) {
		return clientService.editMessage(id, text);
	}
	
	@ResponseBody
	@DeleteMapping("/delete-message/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public String deleteMessage(@PathVariable(required = true) int id) {
		return clientService.deleteMessage(id);
	}
}
