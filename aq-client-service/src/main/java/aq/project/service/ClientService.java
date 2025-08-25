package aq.project.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import aq.project.dto.MessageResponse;
import aq.project.proxy.RestClientProxy;
import aq.project.proxy.RestTemplateProxy;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class ClientService {

	private final RestClientProxy restClientProxy;
	@SuppressWarnings("unused")
	private final RestTemplateProxy restTemplateProxy;
	
	public MessageResponse readMessage(@Positive int id) {
//		return restTemplateProxy.readMessage(id);
		return restClientProxy.readMessage(id);
	}
}
