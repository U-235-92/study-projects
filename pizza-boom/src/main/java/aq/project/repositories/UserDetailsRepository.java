package aq.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import aq.project.entities.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
	
}
