package aq.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aq.project.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

	@Query("SELECT a FROM Authority a WHERE a.name = :name")
	public Optional<Authority> findAuthorityByName(@Param("name") String name);
}
