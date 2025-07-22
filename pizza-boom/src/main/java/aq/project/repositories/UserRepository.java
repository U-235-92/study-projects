package aq.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aq.project.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "SELECT u FROM User u WHERE u.login = :login")
	public User findByLogin(@Param("login") String login);
	
	@Modifying
	@Query(value = "DELETE FROM User u WHERE u.login = :login")
	public void deleteByLogin(@Param("login") String login);
	
	@Modifying
	@Query(value = "UPDATE User u SET u.password = :password WHERE u.login = :login")
	public void updatePassword(@Param("login") String login, @Param("password") String password);
	
	@Modifying
	@Query(value = "UPDATE User u SET "
			+ "u.login = :#{#user.login}, "
			+ "u.password = :#{#user.password} "
			+ "WHERE u.id = :#{#user.id}")
	public void updateUser(@Param("user") User user);
	
	@Modifying
	@NativeQuery(value = "DELETE FROM users_authorities WHERE user_id = :user_id")
	public void deleteUserAuthorities(@Param("user_id") int userId);
	
	@Modifying
	@NativeQuery(value = "INSERT INTO users_authorities VALUES (:authority_id, :user_id)")
	public void insertUserAuthority(@Param("user_id") int userId, @Param("authority_id") int authorityId);
}
