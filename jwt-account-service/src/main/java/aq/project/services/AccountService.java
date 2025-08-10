package aq.project.services;

import org.springframework.stereotype.Service;

import aq.project.dto.AccountResponse;
import aq.project.dto.AccountRequest;
import aq.project.dto.EditRequest;
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

	public void createAccount(AccountRequest accountRequest) {
		Account account = accountMapper.toAccount(accountRequest);
		accountRepository.save(account);
	}

	public void editAccount(String login, EditRequest editRequest) {
		Account account = accountRepository.findByLogin(login).get();
		account.setPassword(editRequest.getPassword());
		account.setRole(Role.valueOf(editRequest.getRole()));
		accountRepository.save(account);
	}

	public void deleteAccount(String login) {
		accountRepository.deleteByLogin(login);
	}
}
