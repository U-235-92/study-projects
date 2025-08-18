package aq.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import aq.project.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findUserByLogin(String login);
}
