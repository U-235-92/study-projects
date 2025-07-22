package aq.project.configs;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import aq.project.entities.User;
import aq.project.entities.UserAuthority;
import aq.project.entities.UserDetails;
import aq.project.entities.UserRole;
import aq.project.repositories.UserAuthorityRepository;
import aq.project.repositories.UserRepository;
import aq.project.repositories.UserRoleRepository;

@Configuration
@EntityScan(basePackages = {"aq.project.entities"})
@EnableJpaRepositories(basePackages = {"aq.project.repositories"})
public class DataConfig {

	@Bean
	@Profile(value = "dev_h2")
	@SuppressWarnings("unused")
	ApplicationRunner preStartApplicationDataLoad(
			UserAuthorityRepository authorityRepository,
			UserRoleRepository roleRepository,
			UserRepository userRepository
			) {
		return args -> {
//			AUTORITIES MANAGEMENT
			UserAuthority readUserAuthority = new UserAuthority("READ_USER");
			UserAuthority updateUserAuthority = new UserAuthority("UPDATE_USER");
			UserAuthority deleteUserAuthority = new UserAuthority("DELETE_USER");
			UserAuthority createUserAuthority = new UserAuthority("CREATE_USER");
			List<UserAuthority> userAuthorities = List.of(readUserAuthority, updateUserAuthority, deleteUserAuthority, createUserAuthority);
			authorityRepository.saveAll(userAuthorities);
//			ROLE MANAGEMENT
			UserRole superAdminRole = new UserRole("SUPER_ADMIN", List.of(readUserAuthority, updateUserAuthority, deleteUserAuthority, createUserAuthority));
			UserRole customerRole = new UserRole("CUSTOMER", List.of(readUserAuthority, updateUserAuthority));
			roleRepository.save(superAdminRole);
			roleRepository.save(customerRole);
			List<UserRole> userRoles = List.of(superAdminRole, customerRole);
			roleRepository.saveAll(userRoles);
//			USER DETAILS MANAGEMENT
			UserDetails aliceDetails = new UserDetails("alice@mail.aq");
			UserDetails alexanderDetails = new UserDetails("alexander@mail.aq");
//			USER MANAGEMENT
			User alice = new User("alice", "123", aliceDetails, superAdminRole);
			User alexander = new User("alexander", "123", alexanderDetails, customerRole);
			List<User> users = List.of(alice, alexander);
			userRepository.saveAll(users);
		};
	}
}
