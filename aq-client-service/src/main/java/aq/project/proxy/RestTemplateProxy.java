package aq.project.proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import aq.project.dto.MessageRequest;
import aq.project.dto.MessageResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component(value = "restTemplateProxy")
public class RestTemplateProxy implements ClientProxy {

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
	
	private final RestTemplate restTemplate;
	private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;
	
	@Override
	public MessageResponse readMessage(int id) {
		RequestEntity<Void> requestEntity = RequestEntity
				.get(uriReadMessage + id)
				.headers(cust -> cust.setBearerAuth(getAccessToken()))
				.build();
		return restTemplate.exchange(requestEntity, MessageResponse.class).getBody();
	}

	@Override
	public String deleteMessage(int id) {
		RequestEntity<Void> requestEntity = RequestEntity
				.delete(uriDeleteMessage + id)
				.headers(cust -> cust.setBearerAuth(getAccessToken()))
				.build();
		return restTemplate.exchange(requestEntity, String.class).getBody();
	}

	@Override
	public String editMessage(int id, String text) {
		RequestEntity<String> requestEntity = RequestEntity
				.patch(uriEditMessage + id)
				.headers(cust -> cust.setBearerAuth(getAccessToken()))
				.body(text);
		return restTemplate.exchange(requestEntity, String.class).getBody();
	}

	@Override
	public String writeMessage(MessageRequest messageRequest) {
		RequestEntity<MessageRequest> requestEntity = RequestEntity
				.post(uriWriteMessage)
				.headers(cust -> cust.setBearerAuth(getAccessToken()))
				.body(messageRequest);
		return restTemplate.exchange(requestEntity, String.class).getBody();
	}
	
	private String getAccessToken() {
		OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId(clientId)
				.principal(clientId)
				.build();
		OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(request);
		System.err.println(client.getAccessToken().getTokenValue());
		return client.getAccessToken().getTokenValue();
	}
}
