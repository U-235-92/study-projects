package aq.project.configs;

import java.util.ArrayList;
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

//	 TODO list: (Порядок не во всех пунктах последователен)
//	1+ Доделать DTO; 
//	2+ Написать мапперы DTO -> Entity, Entity <- DTO; (MapStruct library);  
//	3.1<---  Реализовать сервис UserService; 
//	3.2 При необходимости - дополнить репозитории;
//	4. Написать аспекты сервиса, логирования и валидации;
//	5+ Настроить валидацию сущностей и DTO; 
//	6+ Написать исключения ошибок валидации DTO; 
//	7+ Внедрить @Bean валидатора; 
//	8+ Вывести смену пароля и бан пользователя в отдельные конечные точки 
//	9. Обновить DataConfig
//	10+ Стоит ли сделать в качестве первичного ключа юзера его логин? 
//	11. Вынести смену имени пользователя в отдельную конечную точку (для basic и extended)
//	12+ Добавить хранителя имен конечных точек и файл свойств к ним 
//	13+ Добавить обработку исключений валидности запросов и ответов в контроллер UserController 
//	14. Следует ли включить обработку NoValidUserException в контроллер UseController?
//	15. Что будет если вызвать свойство без {} в MessageFormatter?
//	16+ Создай один класс-иключение для неизвестных (Unknown) свойств
	
	@Bean
	@Profile(value = "dev_db_h2")
	@SuppressWarnings("unused")
	ApplicationRunner preStartApplicationDataLoad(
			PasswordEncoder passwordEncoder,
			UserAuthorityRepository authorityRepository,
			UserRepository userRepository
			) {
		return args -> {
//			AUTHORITIES MANAGEMENT
//			BASIC AUTHORITIES
			UserAuthority basicReadUserAuthority = new UserAuthority("BASIC_READ_USER");
			UserAuthority basicUpdateUserAuthority = new UserAuthority("BASIC_UPDATE_USER");
			UserAuthority basicDeleteUserAuthority = new UserAuthority("BASIC_DELETE_USER");
//			EXTENDED AUTHORITIES			
			UserAuthority extendedCreateUserAuthority = new UserAuthority("EXTENDED_CREATE_USER");
			UserAuthority extendedReadUserAuthority = new UserAuthority("EXTENDED_READ_USER");
			UserAuthority extendedUpdateUserAuthority = new UserAuthority("EXTENDED_UPDATE_USER");
			UserAuthority extendedDeleteUserAuthority = new UserAuthority("EXTENDED_DELETE_USER");
//			SAVE AUTHORITIES PROCESS
			List<UserAuthority> basicUserAuthorities = List.of(basicReadUserAuthority, basicUpdateUserAuthority, basicDeleteUserAuthority);
			List<UserAuthority> extendedUserAuthorities = List.of(extendedCreateUserAuthority, extendedReadUserAuthority, extendedUpdateUserAuthority, extendedDeleteUserAuthority);
			authorityRepository.saveAll(basicUserAuthorities);
			authorityRepository.saveAll(extendedUserAuthorities);
//			USER DETAILS MANAGEMENT
//			UserDetails aliceDetails = new UserDetails("alice@mail.aq");
//			UserDetails alexanderDetails = new UserDetails("alexander@mail.aq");
//			USER MANAGEMENT
//			List<UserAuthority> aliceAuthorities = new ArrayList<>();
//			aliceAuthorities.addAll(extendedUserAuthorities);
//			List<UserAuthority> alexanderAuthorities = new ArrayList<>();
//			alexanderAuthorities.addAll(basicUserAuthorities);
//			User alice = new User("alice", passwordEncoder.encode("123"), aliceDetails, aliceAuthorities);
//			User alexander = new User("alexander", passwordEncoder.encode("321"), alexanderDetails, alexanderAuthorities);
//			List<User> users = List.of(alice, alexander);
//			userRepository.saveAll(users);
		};
	}
}
