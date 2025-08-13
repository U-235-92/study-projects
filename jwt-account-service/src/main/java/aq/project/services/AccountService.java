package aq.project.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import aq.project.dto.AccountRequest;
import aq.project.dto.AccountResponse;
import aq.project.dto.EditRequest;
import aq.project.entities.Account;
import aq.project.entities.Role;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.mappers.AccountMapper;
import aq.project.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
	
	private final AccountMapper accountMapper;
	private final PasswordEncoder passwordEncoder;
	private final AccountRepository accountRepository;

	public AccountResponse readAccount(String login) {
		Account account = findAccountOrThrow(login);
		return accountMapper.toAccountResponse(account);
	}
	
	public void createAccount(AccountRequest accountRequest) {
		Account account = accountMapper.toAccount(accountRequest);
		accountRepository.save(account);
	}

	public void editAccount(String login, EditRequest editRequest) {
		Account account = findAccountOrThrow(login);
		account.setPassword(passwordEncoder.encode(editRequest.getPassword()));
		account.setRole(Role.valueOf(editRequest.getRole()));
		accountRepository.save(account);
	}

	public void deleteAccount(String login) {
		findAccountOrThrow(login);
		accountRepository.deleteByLogin(login);
	}

	public void blockAccount(String login) {
		findAccountOrThrow(login);
		accountRepository.blockAccount(login);
	}

	public void unblockAccount(String login) {
		findAccountOrThrow(login);
		accountRepository.unblockAccount(login);
	}
	
	private Account findAccountOrThrow(String login) {
		return accountRepository.findByLogin(login).orElseThrow(() -> new AccountNotFoundException(login));
	}
}
