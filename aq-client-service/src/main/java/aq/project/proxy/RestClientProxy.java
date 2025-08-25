package aq.project.proxy;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import aq.project.dto.MessageResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestClientProxy {

	private final RestClient restClient;
	private final PasswordEncoder passwordEncoder;
	
	public MessageResponse readMessage(int id) {
		return restClient.get()
			.uri("http://localhost:5085/resource/read-message/" + id)
//			.attribute("client_id", "aq_client_service")
//			.attribute("client_secret", passwordEncoder.encode("secret"))
			.retrieve()
			.body(MessageResponse.class);
	}
}
