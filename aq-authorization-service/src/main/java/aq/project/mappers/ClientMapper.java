package aq.project.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

import aq.project.dto.client.ClientRequest;
import aq.project.dto.client.ClientResponse;
import aq.project.entities.Client;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class ClientMapper {

	@Mapping(target = "scopes", expression = "java(toScopes(clientRequest))")
	abstract Client toClient(ClientRequest clientRequest);
	
	@Named("toScopes")
	protected List<String> toScopes(ClientRequest clientRequest) {
		return clientRequest.getScopes().stream().toList();
	}
	
	@Mapping(target = "scopes", expression = "java(toScopes(client))")
	abstract ClientResponse toClientResponse(Client client);
	
	@Named("toScopes")
	protected List<String> toScopes(Client client) {
		return client.getScopes().stream().toList();
	}
}
