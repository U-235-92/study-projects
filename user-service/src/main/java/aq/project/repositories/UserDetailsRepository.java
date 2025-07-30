package aq.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aq.project.entities.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
	
	@Query("SELECT u FROM UserDetails u WHERE u.email = :email")
	Optional<UserDetails> findByEmail(@Param("email") String email);
}
