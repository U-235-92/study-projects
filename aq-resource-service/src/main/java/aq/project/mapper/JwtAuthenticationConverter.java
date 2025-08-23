package aq.project.mapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import aq.project.security.ResourceServiceAuthenticationToken;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, ResourceServiceAuthenticationToken> {

	@Override
	public ResourceServiceAuthenticationToken convert(Jwt source) {
		return new ResourceServiceAuthenticationToken(source);
	}
}
