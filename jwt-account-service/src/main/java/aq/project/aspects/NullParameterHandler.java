package aq.project.aspects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class NullParameterHandler {

	static void handleNullParameter(Object param, String message) {
		if(param == null || (param instanceof String && ((String) param).toLowerCase().equals("null")))
			throw new NullPointerException(message);
	}
}
