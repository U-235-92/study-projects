package aq.project.config;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class SecurityConfig {

	@Value("${app.keystore.name}")
	private String keyStoreName;
	@Value("${app.keystore.alias}")
	private String keyStoreAlias;
	@Value("${app.keystore.password}")
	private String keyStorePassword;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.oauth2ResourceServer(cust -> cust.jwt(Customizer.withDefaults()));
		
		http.authorizeHttpRequests(cust -> cust.requestMatchers(HttpMethod.GET, "/generate-token").permitAll());
		http.authorizeHttpRequests(cust -> cust.anyRequest().authenticated());
		
		http.httpBasic(Customizer.withDefaults());
		
		http.sessionManagement(cust -> cust.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		UserDetails alice = User.builder()
				.username("alice")
				.password("{noop}5")
				.authorities(List.of(new SimpleGrantedAuthority("READ"), new SimpleGrantedAuthority("WRITE")))
				.build();
		UserDetails alexander = User.builder()
				.username("alexander")
				.password("{noop}8")
				.authorities(List.of(new SimpleGrantedAuthority("READ"), new SimpleGrantedAuthority("WRITE"), new SimpleGrantedAuthority("UPDATE")))
				.build();
		UserDetailsService uds = new InMemoryUserDetailsManager(List.of(alice, alexander));
		return uds;
	}
	
	@Bean
	JwtEncoder jwtEncoder() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableEntryException {
		RSAPublicKey publicKey = getPublicKey();
		RSAPrivateKey privateKey = getPrivateKey();
		JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
	    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
	    return new NimbusJwtEncoder(jwks);
	}
	
	@Bean
	JwtDecoder jwtDecoder() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		RSAPublicKey publicKey = getPublicKey(); 
		return NimbusJwtDecoder.withPublicKey(publicKey).build();
	}
	
	private RSAPrivateKey getPrivateKey() throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, CertificateException, IOException {
		KeyStore keyStore = getKeyStore();
		ProtectionParameter protectionParameter = new PasswordProtection(keyStorePassword.toCharArray());
		PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) keyStore.getEntry(keyStoreAlias, protectionParameter);
		PrivateKey privateKey = privateKeyEntry.getPrivateKey();
		return (RSAPrivateKey) privateKey;
	}
	
	private RSAPublicKey getPublicKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore keyStore = getKeyStore();
		RSAPublicKey publicKey = (RSAPublicKey) keyStore.getCertificate(keyStoreAlias).getPublicKey();
		return publicKey;
	}
	
	private KeyStore getKeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore keyStore = KeyStore.getInstance(new File(keyStoreName), keyStorePassword.toCharArray());
		return keyStore;
	}
}
