package aq.project;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import aq.project.utils.AuthorityNames;

@SpringBootTest
@AutoConfigureMockMvc
class ExtendedUserServiceAuthorizationTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	@WithMockUser(username = "alice", password = "123", authorities = {AuthorityNames.EXTENDED_READ_USER})
	void testAccessAllowToReadAllUsersWhenValidAuthorityAssigned() throws Exception {
		mvc.perform(get("http://localhost:8558/user/extended/read-all")).andExpect(status().isOk());
	}

	@Test
	void testUnauthorizedReadAllUsersWhenNoUserAssigned() throws Exception {
		mvc.perform(get("http://localhost:8558/user/extended/read-all")).andExpect(status().isUnauthorized());
	}
	
	@Test
	@WithMockUser(username = "alexander", password = "321", authorities = {AuthorityNames.BASIC_READ_USER})
	void testForbiddenToReadAllUsersWhenNoValidUserAuthorityAssigned() throws Exception {
		mvc.perform(get("http://localhost:8558/user/extended/read-all")).andExpect(status().isForbidden());
	}
	
	@Test
	@WithMockUser(username = "alice", password = "123", authorities = {AuthorityNames.EXTENDED_READ_USER})
	void testAllowToReadAnyBasicUserDataWhenUserWithExtendedReadUserAuthorityPassed() throws Exception {
		mvc.perform(get("http://localhost:8558/user/extended/read/alexander")).andExpect(status().isOk());
	}
}
