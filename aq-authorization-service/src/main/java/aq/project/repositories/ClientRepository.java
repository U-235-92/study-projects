package aq.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import aq.project.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

	Optional<Client> findClientByName(String name);
}
