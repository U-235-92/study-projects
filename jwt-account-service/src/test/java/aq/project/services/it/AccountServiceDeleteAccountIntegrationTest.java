package aq.project.services.it;

import static aq.project.util.AccountLogins.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
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
class AccountServiceDeleteAccountIntegrationTest {

	@Autowired
	private AccountService accountService;
	@MockitoBean
	private AccountRepository accountRepository;

	@BeforeAll
	private void initRepository() {
		Account johnAcc = new Account(JOHN, "1", true, Role.ADMIN); johnAcc.setId(1);
		Account sarahAcc = new Account(SARAH, "2", true,  Role.EDITOR); sarahAcc.setId(2);
		Account junkoAcc = new Account(JUNKO, "3", true, Role.READER); junkoAcc.setId(3);
		when(accountRepository.findByLogin(null)).thenThrow(NullPointerException.class);
		doReturn(Optional.of(johnAcc)).when(accountRepository).findByLogin(JOHN);
		doReturn(Optional.of(sarahAcc)).when(accountRepository).findByLogin(SARAH);
		doReturn(Optional.of(junkoAcc)).when(accountRepository).findByLogin(JUNKO);
	}
	
	@Test
	@DisplayName("test-success-account-delete")
	void test1() {
		accountService.deleteAccount(SARAH);
		verify(accountRepository).deleteByLogin(SARAH);
	}
	
	@Test
	@DisplayName("test-null-account-delete")
	void test2() {
		verify(accountRepository, never()).delete(Mockito.any());
	}
	
	@Test
	@DisplayName("test-not-found-account-delete")
	void test3() {
		assertThrows(AccountNotFoundException.class, () -> accountService.deleteAccount(NOT_FOUND_LOGIN));
	}
}
