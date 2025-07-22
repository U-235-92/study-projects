package aq.project.configs;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import aq.project.entities.User;
import aq.project.entities.UserAuthority;
import aq.project.entities.UserDetails;
import aq.project.repositories.UserAuthorityRepository;
import aq.project.repositories.UserRepository;

@Configuration
@EntityScan(basePackages = {"aq.project.entities"})
@EnableJpaRepositories(basePackages = {"aq.project.repositories"})
public class DataConfig {

	@Bean
	@Profile(value = "dev_h2")
	@SuppressWarnings("unused")
	ApplicationRunner preStartApplicationDataLoad(
			PasswordEncoder passwordEncoder,
			UserAuthorityRepository authorityRepository,
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
//			USER DETAILS MANAGEMENT
			UserDetails aliceDetails = new UserDetails("alice@mail.aq");
			UserDetails alexanderDetails = new UserDetails("alexander@mail.aq");
//			USER MANAGEMENT
			User alice = new User("alice", passwordEncoder.encode("123"), aliceDetails, List.of(readUserAuthority, updateUserAuthority, deleteUserAuthority, createUserAuthority));
			User alexander = new User("alexander", passwordEncoder.encode("123"), alexanderDetails, List.of(readUserAuthority, updateUserAuthority));
			List<User> users = List.of(alice, alexander);
			userRepository.saveAll(users);
		};
	}
}
