package aq.project.exceptions;

import java.util.Collection;
import java.util.stream.Collectors;

import aq.project.models.InvalidProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidPropertyException extends Exception {

	private static final long serialVersionUID = 1L;

	private String exceptionMessageHeader;
	private Collection<InvalidProperty> invalidProperties;
	
	@Override
	public String getMessage() {
		String invalidPropertiesString = invalidProperties.stream()
				.map(ip -> String.format("%s: %s", ip.getKey(), ip.getValue()))
				.collect(Collectors.joining(",\n"));
		return new StringBuilder(exceptionMessageHeader)
				.append(":\n")
				.append(invalidPropertiesString)
				.toString();
	}
}
