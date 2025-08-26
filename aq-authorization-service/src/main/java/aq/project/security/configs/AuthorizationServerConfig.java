package aq.project.security.configs;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import aq.project.mappers.ClientMapper;
import aq.project.repositories.ClientRepository;
import aq.project.security.utils.JpaRegistredClientService;
import ch.qos.logback.core.net.ssl.KeyStoreFactoryBean;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig {
	
	private final ClientMapper clientMapper;
	private final PasswordEncoder passwordEncoder;
	private final ClientRepository clientRepository;
	
	@Value("${aq.authorization-service.key-store.file}")
	private String keyStoreFile;
	@Value("${aq.authorization-service.key-store.alias}")
	private String keyStoreAlias;
	@Value("${aq.authorization-service.key-store.password}")
	private String keyStorePassword;
	@Value("${aq.jwt.issuer}")
	private String tokenIss;
	@Value("${aq.jwt.exp}")
	private Duration tokenExp;
	
	@Bean
	SecurityFilterChain authorizationServiceSecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/oauth2/**", "/.well-known/**");
		
		http.with(OAuth2AuthorizationServerConfigurer.authorizationServer(), Customizer.withDefaults());
		
		http.getConfigurer(OAuth2AuthorizationServerConfigurer.class);
		
		http.authorizeHttpRequests(cust -> cust.anyRequest().authenticated());
		
		return http.build();
	}
	
	@Bean
	OAuth2TokenCustomizer<JwtEncodingContext> auth2TokenCustomizer() {
		return context -> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Instant now = Instant.now();
			Instant exp = now.plus(tokenExp);
			Set<String> scopes = registeredClientRepository().findByClientId(authentication.getPrincipal().toString()).getScopes();
			JwtClaimsSet claimsSet = JwtClaimsSet.builder()
				.issuer(tokenIss)
				.subject(authentication.getPrincipal().toString())
				.expiresAt(exp)
				.claim("scope", scopes)
				.build();
			context.getClaims()
				.claims(claims -> claimsSet.getClaims().forEach((k, v) -> claims.put(k, v)))
				.build();
		};
	}
	
	@Bean 
	RegisteredClientRepository registeredClientRepository() {
		return new JpaRegistredClientService(clientMapper, passwordEncoder, clientRepository);
	}
 	
	@Bean
	AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}
	
	@Bean
	JWKSource<SecurityContext> jwkSourceInKeyStore() throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException, NoSuchProviderException {
		KeyStore keyStore = getKeyStore();
		RSAPublicKey publicKey = getPublicKey(keyStore);
		RSAPrivateKey privateKey = getPrivateKey(keyStore);
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}
	
	private KeyStore getKeyStore() throws NoSuchProviderException, NoSuchAlgorithmException, KeyStoreException {
		KeyStoreFactoryBean keyStoreFactoryBean = new KeyStoreFactoryBean();
		keyStoreFactoryBean.setType(KeyStore.getDefaultType());
		keyStoreFactoryBean.setLocation("file:" + keyStoreFile);
		keyStoreFactoryBean.setPassword(keyStorePassword);
		return keyStoreFactoryBean.createKeyStore();
	}
	
	private RSAPublicKey getPublicKey(KeyStore keyStore) throws KeyStoreException {
		return (RSAPublicKey) keyStore.getCertificate(keyStoreAlias).getPublicKey();
	}
	
	private RSAPrivateKey getPrivateKey(KeyStore keyStore) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableEntryException {
		ProtectionParameter protectionParameter = new PasswordProtection(keyStorePassword.toCharArray());
		PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) keyStore.getEntry(keyStoreAlias, protectionParameter);
		PrivateKey privateKey = privateKeyEntry.getPrivateKey();
		return (RSAPrivateKey) privateKey;
	}
	
	@Bean 
	@Profile("dev-mem-key-store")
	JWKSource<SecurityContext> jwkSourceInMemeory() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	private static KeyPair generateRsaKey() { 
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}
}
