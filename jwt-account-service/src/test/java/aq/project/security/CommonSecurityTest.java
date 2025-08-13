package aq.project.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import aq.project.dto.EditRequest;
import aq.project.entities.Role;
import aq.project.util.WithJwtAccount;

@SpringBootTest
@AutoConfigureMockMvc
class CommonSecurityTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	@DisplayName("test-forbidden-role-reader-operation")
	@WithMockUser(username = "junko", password = "3", authorities = "ROLE_READER")
	void test1() throws Exception {
		mvc.perform(delete("http://localhost:5082/account/delete/junko")).andExpect(status().isForbidden());
	}

	@Test
	@DisplayName("test-ok-role-reader-operation")
	@WithJwtAccount(login = "junko", isRevoked = false, roles = { "READER" })
	void test2() throws Exception {
		mvc.perform(get("http://localhost:5082/account/read/junko")).andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("test-ok-role-admin-operations")
	void test3() throws Exception {
		UserDetails userDetails = User.builder()
				.username("john")
				.password("1")
				.roles(Role.ADMIN.name())
				.build();
		mvc.perform(delete("http://localhost:5082/account/delete/junko").with(user(userDetails))).andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("test-forbiden-role-editor-operations")
	@WithJwtAccount(login = "sarah", isRevoked = false, roles = { "EDITOR" })
	void test4() throws Exception {
		mvc.perform(delete("http://localhost:5082/account/delete/john")).andExpect(status().isForbidden());
	}
	
	@Test
	@DisplayName("test-ok-role-editor-operations")
	@WithJwtAccount(login = "sarah", isRevoked = false, roles = { "EDITOR" })
	void test5() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		EditRequest editRequest = new EditRequest("22", Role.EDITOR.name());
		String jsonEditRequest = objectMapper.writeValueAsString(editRequest);
		System.out.println(jsonEditRequest);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.patch("http://localhost:5082/account/edit/sarah")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonEditRequest);
		mvc.perform(builder);
	}
	
	@Test
	@DisplayName("test-revoked-account-operations")
	@WithJwtAccount(login = "sarah", isRevoked = true, roles = { "EDITOR" })
	void test6() throws Exception {
		mvc.perform(patch("http://localhost:5082/account/edit/sarah")).andExpect(status().isForbidden());
	}
}
