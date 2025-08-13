package aq.project.services.it;

import static aq.project.util.AccountLogins.JOHN;
import static aq.project.util.AccountLogins.JUNKO;
import static aq.project.util.AccountLogins.NOT_FOUND_LOGIN;
import static aq.project.util.AccountLogins.SARAH;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import aq.project.entities.Account;
import aq.project.entities.Role;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.repositories.AccountRepository;
import aq.project.services.AccountService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class AccountServiceReadAccountIntegrationTest {

	@Autowired
	@InjectMocks
	private AccountService accountService;
	@MockitoBean
	private AccountRepository accountRepository;

	@BeforeAll
	private void initRepository() {
		Account johnAcc = new Account(JOHN, "1", true, Role.ADMIN); johnAcc.setId(1);
		Account sarahAcc = new Account(SARAH, "2", true, Role.EDITOR); sarahAcc.setId(2);
		Account junkoAcc = new Account(JUNKO, "3", true, Role.READER); junkoAcc.setId(3);
		when(accountRepository.findByLogin(null)).thenThrow(NullPointerException.class);
		doReturn(Optional.of(johnAcc)).when(accountRepository).findByLogin(JOHN);
		doReturn(Optional.of(sarahAcc)).when(accountRepository).findByLogin(SARAH);
		doReturn(Optional.of(junkoAcc)).when(accountRepository).findByLogin(JUNKO);
	}
	
	@Test
	@DisplayName("success-read-account-test")
	void test1() {
		assertAll(() -> accountService.readAccount(JUNKO));
	}
	
	@Test
	@DisplayName("not-found-login-fail-read-account-test")
	void test2() {
		assertThrows(AccountNotFoundException.class, () -> accountService.readAccount(NOT_FOUND_LOGIN));
	}
	
	@Test
	@DisplayName("null-login-fail-read-account-test")
	void test3() {
		assertThrows(NullPointerException.class, () -> accountService.readAccount(null));
	}
}
