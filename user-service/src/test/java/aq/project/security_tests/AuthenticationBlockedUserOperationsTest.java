package aq.project.security_tests;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Profile("dev_db_h2")
class AuthenticationBlockedUserOperationsTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	@Disabled
//	To pass test change isNotBlocked value of user in DataConfig
	void testAccessDeniedToPerformOperationsToBlockedUser() throws Exception {
		mvc.perform(get("http://localhost:8558/user/basic/read/alexander").with(httpBasic("alexander", "321")))
			.andExpect(status().isForbidden());
	}
	
	@Test
	void testAccessAllowedToPerformOperationsToNotBlockedUser() throws Exception {
		mvc.perform(get("http://localhost:8558/user/basic/read/alexander").with(httpBasic("alexander", "321")))
			.andExpect(status().isOk());
	}
}
