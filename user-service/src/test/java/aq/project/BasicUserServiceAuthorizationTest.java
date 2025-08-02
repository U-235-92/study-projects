package aq.project;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import aq.project.utils.AuthorityNames;

@SpringBootTest
@AutoConfigureMockMvc
class BasicUserServiceAuthorizationTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	@WithMockUser(username = "alexander")
	void testForbiddenToReadBasicUserDataWhenDifferentLoginPassed() throws Exception {
		mvc.perform(get("http://localhost:8558/user/basic/read/alice"))
			.andExpect(status().isForbidden());
	}
	
	@Test
	void testAllowToReadBasicUserDataWhenSameLoginPassed() throws Exception {
		UserDetails user = User.withUsername("alexander")
				.password("321")
				.authorities(List.of(new SimpleGrantedAuthority(AuthorityNames.BASIC_READ_USER)))
				.build();
		mvc.perform(get("http://localhost:8558/user/basic/read/alexander")
				.with(user(user)))
			.andExpect(status().isOk());
	}
}
