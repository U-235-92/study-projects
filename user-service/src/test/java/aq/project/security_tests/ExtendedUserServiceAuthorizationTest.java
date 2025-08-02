package aq.project.security_tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import aq.project.security_tests.utils.WithCustomUser;
import aq.project.utils.AuthorityNames;

@SpringBootTest
@AutoConfigureMockMvc
@Profile("dev_db_h2")
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
	
	@Test
	@WithCustomUser(login = "alice", password = "123", authorities = {AuthorityNames.EXTENDED_DELETE_USER})
	void testUnableToDeleteNotExistUser() throws Exception {
		mvc.perform(delete("http://localhost:8558/user/extended/delete/john")).andExpect(status().isBadRequest());
	}
}
