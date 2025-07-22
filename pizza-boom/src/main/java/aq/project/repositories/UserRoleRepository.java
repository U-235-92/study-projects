package aq.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import aq.project.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

}
