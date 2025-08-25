package aq.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ClientServiceWebConfiguration {

	private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;
	
	@Bean
	RestClient restClient() {
		OAuth2ClientHttpRequestInterceptor interceptor = new OAuth2ClientHttpRequestInterceptor(oAuth2AuthorizedClientManager);
		RestClient restClient = RestClient.builder().requestInterceptor(interceptor).build();
		return restClient;
	}
	
	@Bean 
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
