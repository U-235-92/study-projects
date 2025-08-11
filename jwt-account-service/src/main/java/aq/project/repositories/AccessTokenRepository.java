package aq.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aq.project.entities.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {

	@Query("SELECT at FROM AccessToken at WHERE at.account.login = :login")
	AccessToken findByLogin(@Param("login") String login);

	@Modifying
	@Query("DELETE FROM AccessToken at WHERE at.account.login = :login")
	void deleteByLogin(@Param("login") String login);
}
