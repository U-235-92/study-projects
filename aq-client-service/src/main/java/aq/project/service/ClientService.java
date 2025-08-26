package aq.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import aq.project.dto.MessageRequest;
import aq.project.dto.MessageResponse;
import aq.project.proxy.ClientProxy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
public class ClientService {

	@Autowired
	@Qualifier("restClientProxy")
	private ClientProxy clientProxy;
	
	public MessageResponse readMessage(@Positive int id) {
		return clientProxy.readMessage(id);
	}
	
	public String writeMessage(@NotNull MessageRequest messageRequest) {
		return clientProxy.writeMessage(messageRequest);
	}
	
	public String editMessage(@Positive int id, @NotNull String text) {
		return clientProxy.editMessage(id, text);
	}
	
	public String deleteMessage(@Positive int id) {
		return clientProxy.deleteMessage(id);
	}
}
