package aq.project.util;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RUNTIME)
@Target(METHOD)
@WithSecurityContext(factory = JwtAccountSecurityContext.class)
public @interface WithJwtAccount {

	String login();
	boolean isRevoked();
	String[] roles();
}
