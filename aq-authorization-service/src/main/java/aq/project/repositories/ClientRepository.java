package aq.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import aq.project.entities.Client;

public interface ClientRepository extends JpaRepository<Client, String> {

	Optional<Client> findByName(String name);
	
	void deleteByName(String name);
}
