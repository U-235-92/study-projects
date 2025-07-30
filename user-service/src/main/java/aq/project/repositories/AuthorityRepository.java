package aq.project.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aq.project.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

	@Query("SELECT a FROM Authority a WHERE a.name = :name")
	public Optional<Authority> findByName(@Param("name") String name);
	
	@NativeQuery("SELECT * FROM authorities WHERE "
			+ "id IN (SELECT authority_id FROM users_authorities WHERE "
				+ "user_id = (SELECT id FROM users WHERE login = :login))")
	public List<Authority> findUserAuthorities(@Param("login") String login);
	
	@Modifying
	@Query("DELETE FROM Authority a WHERE a.name = :name")
	public void deleteByName(@Param("name") String name);
}
