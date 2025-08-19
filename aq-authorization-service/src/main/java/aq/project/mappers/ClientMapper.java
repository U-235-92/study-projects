package aq.project.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import aq.project.entities.Client;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class ClientMapper {

	@Mapping(target = "id", source = "id")
	@Mapping(target = "name", source = "clientId")
	@Mapping(target = "secret", source = "clientSecret")
	@Mapping(target = "scopes", source = "scopes")
	@Mapping(target = "authenticationMethod", expression = "java(toAuthenticationMethod(registeredClient))")
	@Mapping(target = "authorizationGrantType", expression = "java(toGrantType(registeredClient))")
	public abstract Client toClient(RegisteredClient registeredClient);
	
	protected String toAuthenticationMethod(RegisteredClient registeredClient) {
		return registeredClient.getClientAuthenticationMethods().stream().findFirst().get().getValue();
	}
	
	protected String toGrantType(RegisteredClient registeredClient) {
		return registeredClient.getAuthorizationGrantTypes().stream().findFirst().get().getValue();
	}
	
	public RegisteredClient toRegistredClient(Client client) {
		return RegisteredClient.withId(client.getId())
				.clientId(client.getName())
				.clientSecret(client.getSecret())
				.scopes(consumer -> consumer.addAll(client.getScopes()))
				.clientAuthenticationMethod(ClientAuthenticationMethod.valueOf(client.getAuthenticationMethod()))
				.authorizationGrantType(new AuthorizationGrantType(client.getAuthorizationGrantType()))
				.build();
	}
}
