package aq.project.configs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import aq.project.entities.User;
import aq.project.entities.Authority;
import aq.project.entities.UserDetails;
import aq.project.repositories.AuthorityRepository;
import aq.project.repositories.UserRepository;
import aq.project.utils.AuthorityNames;

@Configuration
@EntityScan(basePackages = {"aq.project.entities"})
@EnableJpaRepositories(basePackages = {"aq.project.repositories"})
public class DataConfig {
	
	@Value("${date-format}")
	private String dateFormat;
	
	@Bean
	@Profile(value = "dev_db_h2")
	@SuppressWarnings("unused")
	ApplicationRunner preStartApplicationDataLoad(
			PasswordEncoder passwordEncoder,
			AuthorityRepository authorityRepository,
			UserRepository userRepository
			) {
		return args -> {
//			AUTHORITIES MANAGEMENT
//			BASIC AUTHORITIES
			Authority basicReadUserAuthority = new Authority(AuthorityNames.BASIC_READ_USER);
			Authority basicUpdateUserAuthority = new Authority(AuthorityNames.BASIC_UPDATE_USER);
			Authority basicDeleteUserAuthority = new Authority(AuthorityNames.BASIC_DELETE_USER);
//			EXTENDED AUTHORITIES			
			Authority extendedCreateUserAuthority = new Authority(AuthorityNames.EXTENDED_CREATE_USER);
			Authority extendedReadUserAuthority = new Authority(AuthorityNames.EXTENDED_READ_USER);
			Authority extendedUpdateUserAuthority = new Authority(AuthorityNames.EXTENDED_UPDATE_USER);
			Authority extendedDeleteUserAuthority = new Authority(AuthorityNames.EXTENDED_DELETE_USER);
			Authority createAuthority = new Authority(AuthorityNames.CREATE_AUTHORITY);
			Authority readAuthority = new Authority(AuthorityNames.READ_AUTHORITY);
			Authority updateAuthority = new Authority(AuthorityNames.UPDATE_AUTHORITY);
			Authority deleteAuthority = new Authority(AuthorityNames.DELETE_AUTHORITY);
//			SAVE AUTHORITIES PROCESS
			List<Authority> basicUserAuthorities = List.of(basicReadUserAuthority, 
					basicUpdateUserAuthority, 
					basicDeleteUserAuthority);
			List<Authority> extendedUserAuthorities = List.of(extendedCreateUserAuthority, 
					extendedReadUserAuthority, 
					extendedUpdateUserAuthority, 
					extendedDeleteUserAuthority,
					createAuthority,
					readAuthority,
					updateAuthority,
					deleteAuthority);
			authorityRepository.saveAll(basicUserAuthorities);
			authorityRepository.saveAll(extendedUserAuthorities);
//			USER DETAILS MANAGEMENT
			UserDetails aliceDetails = new UserDetails("Alice", "K", "alice@mail.aq", LocalDate.parse("15-11-2028", DateTimeFormatter.ofPattern(dateFormat)));
			UserDetails alexanderDetails = new UserDetails("Alexander", "K", "alexander@mail.aq", LocalDate.parse("15-05-2030", DateTimeFormatter.ofPattern(dateFormat)));
//			USER MANAGEMENT
			List<Authority> aliceAuthorities = new ArrayList<>();
			aliceAuthorities.addAll(extendedUserAuthorities);
			List<Authority> alexanderAuthorities = new ArrayList<>();
			alexanderAuthorities.addAll(basicUserAuthorities);
			User alice = new User("alice", passwordEncoder.encode("123"), true, aliceDetails, aliceAuthorities);
			User alexander = new User("alexander", passwordEncoder.encode("321"), true, alexanderDetails, alexanderAuthorities);
			List<User> users = List.of(alice, alexander);
			userRepository.saveAll(users);
		};
	}
}
