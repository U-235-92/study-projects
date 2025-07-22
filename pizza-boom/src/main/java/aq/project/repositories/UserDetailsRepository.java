package aq.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aq.project.entities.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

	@Modifying
	@Query(value = "UPDATE UserDetails ud SET "
			+ "ud.email = :#{#userDetails.email} "
			+ "WHERE ud.id = :#{#userDetails.id}")
	public void updateUserDetails(@Param("userDetails") UserDetails userDetails);
}
