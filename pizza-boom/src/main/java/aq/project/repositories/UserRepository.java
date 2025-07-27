package aq.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aq.project.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

	@Query(value = "SELECT u FROM User u WHERE u.login = :login")
	public Optional<User> findByLogin(@Param("login") String login);
	
	@Modifying
	@Query(value = "DELETE FROM User u WHERE u.login = :login")
	public void deleteByLogin(@Param("login") String login);
	
	@Modifying
	@Query(value = "UPDATE User u SET u.password = :password, u.updatedAt = :updatedAt WHERE u.login = :login")
	public void updatePassword(@Param("login") String login, @Param("password") String password, @Param("updatedAt") long updatedAt);
	
	@Modifying
	@Query(value = "UPDATE User u SET u.login = :newLogin, u.updatedAt = :updatedAt WHERE u.login = :oldLogin")
	public void updateLogin(@Param("newLogin") String newLogin, @Param("oldLogin") String oldLogin, @Param("updatedAt") long updatedAt);
	
	@Modifying
	@Query(value = "UPDATE User u SET u.notBanned = true, u.updatedAt = :updatedAt WHERE u.login = :login")
	public void blockUser(@Param("login") String login, @Param("updatedAt") long updatedAt);
	@Modifying
	@Query(value = "UPDATE User u SET u.notBanned = false, u.updatedAt = :updatedAt WHERE u.login = :login")
	public void unblockUser(@Param("login") String login, @Param("updatedAt") long updatedAt);
}
