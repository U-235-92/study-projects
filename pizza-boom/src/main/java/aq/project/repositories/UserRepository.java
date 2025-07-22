package aq.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import aq.project.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
