package aq.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import aq.project.entities.UserAuthority;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Integer> {

}
