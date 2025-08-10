package aq.project.services.it;

import static aq.project.util.AccountLogins.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import aq.project.dto.EditRequest;
import aq.project.entities.Account;
import aq.project.entities.Role;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.exceptions.EditAccountRequestException;
import aq.project.repository.AccountRepository;
import aq.project.services.AccountService;


@SpringBootTest
class AccountServiceEditAccountIntegrationTest {
	
	@Autowired
	private AccountService accountService;
	@MockitoBean
	private AccountRepository accountRepository;
	
	@Test
	@DisplayName("success-account-edition-test")
	void test1() {
		Account oldJunkoAccount = new Account(JUNKO, "3", true, Role.READER);
		Account editJunkoAccount = new Account(JUNKO, "58", true, Role.ADMIN);
		when(accountRepository.findByLogin(JUNKO)).thenReturn(Optional.of(oldJunkoAccount));
		EditRequest accountRequest = new EditRequest("58", Role.ADMIN.name());
		accountService.editAccount(JUNKO, accountRequest);
		verify(accountRepository).save(editJunkoAccount);
	}
	
	@Test
	@DisplayName("not-found-login-account-edition-test")
	void test2() {
		Account oldJunkoAccount = new Account(JUNKO, "3", true, Role.READER);
		Account editJunkoAccount = new Account(JUNKO, "58", true, Role.ADMIN);
		when(accountRepository.findByLogin(JUNKO)).thenReturn(Optional.of(oldJunkoAccount));
		EditRequest accountRequest = new EditRequest("58", Role.ADMIN.name());
		assertThrows(AccountNotFoundException.class, () -> accountService.editAccount(NOT_FOUND_LOGIN, accountRequest));
		verify(accountRepository, never()).save(editJunkoAccount);
	}
	
	@Test
	@DisplayName("null-login-account-edition-test")
	void test3() {
		Account oldJunkoAccount = new Account(JUNKO, "3", true, Role.READER);
		Account editJunkoAccount = new Account(JUNKO, "58", true, Role.ADMIN);
		when(accountRepository.findByLogin(JUNKO)).thenReturn(Optional.of(oldJunkoAccount));
		EditRequest accountRequest = new EditRequest("58", Role.ADMIN.name());
		assertThrows(NullPointerException.class, () -> accountService.editAccount(null, accountRequest));
		verify(accountRepository, never()).save(editJunkoAccount);
	}
	
	@Test
	@DisplayName("null-account-edit-request-account-edition-test")
	void test4() {
		Account oldJunkoAccount = new Account(JUNKO, "3", true, Role.READER);
		Account editJunkoAccount = new Account(JUNKO, "58", true, Role.ADMIN);
		when(accountRepository.findByLogin(JUNKO)).thenReturn(Optional.of(oldJunkoAccount));
		assertThrows(NullPointerException.class, () -> accountService.editAccount(JUNKO, null));
		verify(accountRepository, never()).save(editJunkoAccount);
	}
	
	@Test
	@DisplayName("wrong-account-request-data-edition-test")
	void test5() {
		Account oldJunkoAccount = new Account(JUNKO, "3", true, Role.READER);
		Account editJunkoAccount = new Account(JUNKO, "58", true, Role.ADMIN);
		when(accountRepository.findByLogin(JUNKO)).thenReturn(Optional.of(oldJunkoAccount));
		EditRequest accountRequest = new EditRequest("", Role.ADMIN.name());
		assertThrows(EditAccountRequestException.class, () -> accountService.editAccount(JUNKO, accountRequest));
		verify(accountRepository, never()).save(editJunkoAccount);
	}
}
