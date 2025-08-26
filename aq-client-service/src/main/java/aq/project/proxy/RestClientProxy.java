package aq.project.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import aq.project.dto.MessageRequest;
import aq.project.dto.MessageResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component(value = "restClientProxy")
public class RestClientProxy implements ClientProxy {

	@Value("${aq.client-service.client-id}")
	private String clientId;
	@Value("${aq.uri.request.get.read-message}")
	private String uriReadMessage;
	@Value("${aq.uri.request.patch.edit-message}")
	private String uriEditMessage;
	@Value("${aq.uri.request.post.write-message}")
	private String uriWriteMessage;
	@Value("${aq.uri.request.delete.read-message}")
	private String uriDeleteMessage;
	
	private final RestClient restClient;
	
	@Override
	public MessageResponse readMessage(int id) {
		return restClient.get()
			.uri(uriReadMessage + id)
			.attributes(RequestAttributeClientRegistrationIdResolver.clientRegistrationId(clientId))
			.retrieve()
			.body(MessageResponse.class);
	}

	@Override
	public String deleteMessage(int id) {
		return restClient.delete()
				.uri(uriDeleteMessage + id)
				.attributes(RequestAttributeClientRegistrationIdResolver.clientRegistrationId(clientId))
				.retrieve()
				.body(String.class);
	}

	@Override
	public String editMessage(int id, String text) {
		return restClient.patch()
				.uri(uriEditMessage + id)
				.attributes(RequestAttributeClientRegistrationIdResolver.clientRegistrationId(clientId))
				.body(text)
				.retrieve()
				.body(String.class);
	}

	@Override
	public String writeMessage(MessageRequest messageRequest) {
		return restClient.post()
				.uri(uriWriteMessage)
				.attributes(RequestAttributeClientRegistrationIdResolver.clientRegistrationId(clientId))
				.body(messageRequest)
				.retrieve()
				.body(String.class);
	}
}
