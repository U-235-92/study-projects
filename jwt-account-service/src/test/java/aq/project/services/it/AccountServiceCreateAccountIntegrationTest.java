package aq.project.services.it;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import aq.project.dto.AccountRequest;
import aq.project.entities.Account;
import aq.project.entities.Role;
import aq.project.exceptions.AccountAlreadyExistException;
import aq.project.exceptions.CreateAccountRequestException;
import aq.project.mappers.AccountMapper;
import aq.project.repository.AccountRepository;
import aq.project.services.AccountService;

@SpringBootTest
class AccountServiceCreateAccountIntegrationTest {

	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AccountService accountService;
	@MockitoBean
	private AccountRepository accountRepository;
	
	@Test
	@DisplayName("success-account-create-test")
	void test1() {
		AccountRequest request = new AccountRequest("smith", "8", Role.EDITOR.name());
		accountService.createAccount(request);
		verify(accountRepository).save(accountMapper.toAccount(request));
	}
	
	@Test
	@DisplayName("wrong-account-request-fail-create-test")
	void test2() {
		AccountRequest request = new AccountRequest("smith", "8", "WRONG_ROLE");
		assertThrows(CreateAccountRequestException.class, () -> accountService.createAccount(request));
		verify(accountRepository, never()).save(any());
	}
	
	@Test
	@DisplayName("exist-account-request-fail-create-test")
	void test3() {
		when(accountRepository.findByLogin("alice")).thenReturn(Optional.of(new Account("alice", "5", true, Role.ADMIN)));
		AccountRequest request = new AccountRequest("alice", "8", Role.READER.name());
		assertThrows(AccountAlreadyExistException.class, () -> accountService.createAccount(request));
		verify(accountRepository, never()).save(accountMapper.toAccount(request));
	}
	
	@Test
	@DisplayName("null-account-fail-create-test")
	void test4() {
		assertThrows(NullPointerException.class, () -> accountService.createAccount(null));
		verify(accountRepository, never()).save(accountMapper.toAccount(Mockito.any()));
	}
}
