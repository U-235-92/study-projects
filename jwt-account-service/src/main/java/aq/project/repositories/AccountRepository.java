package aq.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aq.project.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	@Modifying
	@Query("UPDATE Account a SET a.password = :pwd WHERE a.id = :id")
	void updatePassword(@Param("id") int id, @Param("pwd") String password);
	
	@Query("SELECT a FROM Account a WHERE a.login = :login")
	Optional<Account> findByLogin(@Param("login") String login);

	@Modifying
	@Query("DELETE FROM Account a WHERE a.login = :login")
	void deleteByLogin(@Param("login") String login);
	
	@Modifying
	@NativeQuery("UPDATE accounts SET is_not_blocked = false WHERE login = :login")
	void blockAccount(@Param("login") String login);
	
	@Modifying
	@NativeQuery("UPDATE accounts SET is_not_blocked = true WHERE login = :login")
	void unblockAccount(@Param("login") String login);
}
