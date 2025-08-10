package aq.project.util;

import static aq.project.util.AccountLogins.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.mockito.Mockito;

import aq.project.dto.AccountRequest;
import aq.project.dto.AccountResponse;
import aq.project.entities.Account;
import aq.project.entities.Role;
import aq.project.mappers.AccountMapper;
import aq.project.repository.AccountRepository;
import aq.project.services.AccountService;

public class AccountServiceInitializer {

	private AccountMapper accountMapper;
	private AccountService accountService;
	private AccountRepository accountRepository;
	private Map<String, Account> accountMap = new HashMap<>();
	private Map<String, AccountRequest> accountRequestMap = new HashMap<>();
	
	public AccountServiceInitializer() {
		super();
		initAccountMap(); 
		initAccountRequestMap();
		initAccountRepositoryData();
		initAccountMapper();
		initAccountService();
	}
	
	private Map<String, Account> initAccountMap() {
		Account johnAcc = new Account(JOHN, "1", Role.ADMIN); johnAcc.setId(1);
		Account sarahAcc = new Account(SARAH, "2", Role.EDITOR); sarahAcc.setId(2);
		Account junkoAcc = new Account(JUNKO, "3", Role.READER); junkoAcc.setId(3);
		accountMap.put(JOHN, johnAcc);
		accountMap.put(SARAH, sarahAcc);
		accountMap.put(JUNKO, junkoAcc);
		return accountMap;
	}
	
	private Map<String, AccountRequest> initAccountRequestMap() {
		AccountRequest johnRequest = new AccountRequest(JOHN, "1", Role.ADMIN.name());
		AccountRequest sarahRequest = new AccountRequest(SARAH, "2", Role.EDITOR.name());
		AccountRequest junkoRequest = new AccountRequest(JUNKO, "3", Role.READER.name());
		accountRequestMap.put(JOHN, johnRequest);
		accountRequestMap.put(SARAH, sarahRequest);
		accountRequestMap.put(JUNKO, junkoRequest);
		return accountRequestMap;
	}
	
	private AccountRepository initAccountRepositoryData() {
		accountRepository = Mockito.mock(AccountRepository.class);
		when(accountRepository.findByLogin(JOHN)).thenReturn(Optional.of(accountMap.get(JOHN)));
		when(accountRepository.findByLogin(SARAH)).thenReturn(Optional.of(accountMap.get(SARAH)));
		when(accountRepository.findByLogin(JUNKO)).thenReturn(Optional.of(accountMap.get(JUNKO)));
		return accountRepository;
	}
	
	private AccountMapper initAccountMapper() {
		AccountResponse johnResponse = new AccountResponse(
				accountMap.get(JOHN).getId(), 
				accountMap.get(JOHN).getLogin(), 
				accountMap.get(JOHN).getRole().name());
		AccountResponse sarahResponse = new AccountResponse(
				accountMap.get(SARAH).getId(), 
				accountMap.get(SARAH).getLogin(), 
				accountMap.get(SARAH).getRole().name());
		AccountResponse junkoResponse = new AccountResponse(
				accountMap.get(JUNKO).getId(), 
				accountMap.get(JUNKO).getLogin(), 
				accountMap.get(JUNKO).getRole().name());
		accountMapper = Mockito.mock(AccountMapper.class);
		doReturn(johnResponse).when(accountMapper).toAccountResponse(accountMap.get(JOHN));
		doReturn(sarahResponse).when(accountMapper).toAccountResponse(accountMap.get(SARAH));
		doReturn(junkoResponse).when(accountMapper).toAccountResponse(accountMap.get(JUNKO));
		doReturn(getAccount(JOHN)).when(accountMapper).toAccount(getAccountRequest(JOHN));
		doReturn(getAccount(SARAH)).when(accountMapper).toAccount(getAccountRequest(SARAH));
		doReturn(getAccount(JUNKO)).when(accountMapper).toAccount(getAccountRequest(JUNKO));
		return accountMapper;
	}
	
	private void initAccountService() {
		accountService = new AccountService(accountMapper, accountRepository);
	}
	
	public AccountService getAccountService() {
		return accountService;
	}
	
	public AccountRepository getAccountRepository() {
		return accountRepository;
	}
	
	public AccountMapper getAccountMapper() {
		return accountMapper;
	}
	
	public Account getAccount(String login) {
		return accountMap.get(login);
	}
	
	public AccountRequest getAccountRequest(String login) {
		return accountRequestMap.get(login);
	}
}
