package aq.project.proxy;

import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import aq.project.dto.MessageResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestTemplateProxy {

	private final RestTemplate restTemplate;
	private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;
	
	public MessageResponse readMessage(int id) {
		String uri = "http://localhost:5085/resource/read-message/" + id;
		String bearerToken = getBearerToken(id);
		System.err.println(bearerToken);
		RequestEntity<Void> requestEntity = RequestEntity.get(uri).header("Authorization", bearerToken).build();
		return restTemplate.exchange(requestEntity, MessageResponse.class).getBody();
	}
	
	private String getBearerToken(int id) {
		return "Bearer " + getAccessToken(id);
	}
	
	private String getAccessToken(int id) {
		OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId("1")
				.principal("aq_client_service")
				.build();
		OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(request);
		return client.getAccessToken().getTokenValue();
	}
}
