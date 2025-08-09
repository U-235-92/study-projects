package aq.project.services;

import org.springframework.stereotype.Service;

import aq.project.dto.AccountResponse;
import aq.project.dto.CreateAccountRequest;
import aq.project.dto.EditAccountRequest;
import aq.project.entities.Account;
import aq.project.entities.Role;
import aq.project.mappers.AccountMapper;
import aq.project.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

	private final AccountMapper accountMapper;
	private final AccountRepository accountRepository;

	public AccountResponse readAccount(String login) {
		Account account = accountRepository.findByLogin(login).get();
		return accountMapper.toAccountResponse(account);
	}

	public void createAccount(CreateAccountRequest accountRequest) {
		Account account = accountMapper.toAccount(accountRequest);
		accountRepository.save(account);
	}

	public void editAccount(String login, EditAccountRequest accountRequest) {
		Account account = accountRepository.findByLogin(login).get();
		account.setPassword(accountRequest.getPassword());
		account.setRole(Role.valueOf(accountRequest.getRole()));
		accountRepository.save(account);
	}

	public void deleteAccount(String login) {
		accountRepository.deleteByLogin(login);
	}
}
