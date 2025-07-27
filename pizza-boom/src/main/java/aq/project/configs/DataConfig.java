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

//	 TODO list: (Порядок не во всех пунктах последователен)
//	1+ Доделать DTO; 
//	2+ Написать мапперы DTO -> Entity, Entity <- DTO; (MapStruct library);  
//	3.1+  Реализовать сервис UserService; 
//	3.2+ При необходимости - дополнить репозитории;
//	4+  Написать аспекты сервиса и валидации;
//	5+ Настроить валидацию сущностей и DTO; 
//	6+ Написать исключения ошибок валидации DTO; 
//	7+ Внедрить @Bean валидатора; 
//	8+ Вывести смену пароля и бан пользователя в отдельные конечные точки 
//	9+ Обновить DataConfig
//	10+ Стоит ли сделать в качестве первичного ключа юзера его логин? 
//	11+ Вынести смену имени пользователя в отдельную конечную точку (для basic и extended)
//	12+ Добавить хранителя имен конечных точек и файл свойств к ним 
//	13+ Добавить обработку исключений валидности запросов и ответов в контроллер UserController 
//	14+ Следует ли включить обработку NoValidUserException в контроллер UseController?
//	15+ Что будет если вызвать свойство без {} в MessageFormatter?
//	16+ Создай один класс-иключение для неизвестных (Unknown) свойств
	
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
//			SAVE AUTHORITIES PROCESS
			List<Authority> basicUserAuthorities = List.of(basicReadUserAuthority, basicUpdateUserAuthority, basicDeleteUserAuthority);
			List<Authority> extendedUserAuthorities = List.of(extendedCreateUserAuthority, extendedReadUserAuthority, extendedUpdateUserAuthority, extendedDeleteUserAuthority);
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
