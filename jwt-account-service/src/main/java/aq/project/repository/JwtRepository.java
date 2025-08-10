package aq.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

public interface JwtRepository extends JpaRepository<String, String> {

	@Modifying
	@NativeQuery("INSERT INTO jwts (login, access_token) VALUES (:login, :access_token)")
	void saveAccessToken(@Param("login") String login, @Param("access_token") String accessToken);
	
	@Modifying
	@NativeQuery("UPDATE jwts SET access_token = :access_token WHERE login = :login")
	void updateAccessToken(@Param("login") String login, @Param("access_token") String accessToken);
	
	@NativeQuery("SELECT (access_token) FROM jwts WHERE login = :login")
	String findAccessTokenById(@Param("login") String login);
}
