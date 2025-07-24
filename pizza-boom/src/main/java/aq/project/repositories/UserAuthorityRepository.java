package aq.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aq.project.entities.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Integer> {

	@Query("SELECT a FROM UserAuthority a WHERE a.name = :name")
	public Optional<UserAuthority> findAuthorityByName(@Param("name") String name);
}
