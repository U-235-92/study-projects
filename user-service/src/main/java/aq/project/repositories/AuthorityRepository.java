package aq.project.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aq.project.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

	@Query("SELECT a FROM Authority a WHERE a.name = :name")
	public Optional<Authority> findByName(@Param("name") String name);
	
	@NativeQuery("SELECT * FROM authorities WHERE "
			+ "id IN (SELECT authority_id FROM users_authorities WHERE "
				+ "user_id = (SELECT id FROM users WHERE login = :login))")
	public List<Authority> findUserAuthorities(@Param("login") String login);
	
	@Modifying
	@Query("DELETE FROM Authority a WHERE a.name = :name")
	public void deleteByName(@Param("name") String name);
	
	@Modifying
	@NativeQuery("DELETE FROM users_authorities WHERE authority_id = :authority_id")
	public void deleteAuthorityFromUsersAuthorities(@Param("authority_id") int id);
	
	@NativeQuery("SELECT id FROM authorities WHERE name = :name")
	public int findIdByName(@Param("name") String name);
	
	@Modifying
	@NativeQuery("INSERT INTO users_authorities VALUES ("
			+ "(SELECT id FROM authorities WHERE name = :authority), "
			+ "(SELECT id FROM users WHERE login = :login))")
	public void updateUsersAuthoritiesByLoginAndAuthority(@Param("login") String login, @Param("authority") String authority);
	
	@NativeQuery(name = "users_authorities_table_mapping", 
			value = "SELECT user_id, authority_id FROM users_authorities WHERE "
					+ "user_id = (SELECT id FROM users WHERE login = :login) AND "
					+ "authority_id = (SELECT id FROM authorities WHERE name = :authority)")
	public List<Object[]> findAuthorityAndUserIdentificatorsByLoginAndAuthority(@Param("login") String login, @Param("authority") String authority);
	
}
