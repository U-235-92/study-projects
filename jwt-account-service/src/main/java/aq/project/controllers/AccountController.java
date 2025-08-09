package aq.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dto.CreateAccountRequest;
import aq.project.dto.EditAccountRequest;
import aq.project.dto.AccountResponse;
import aq.project.services.AccountService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

	private final AccountService accountService;
	
	@GetMapping("/read/{login}")
	public AccountResponse readAccount(@PathVariable(required = true) String login) {
		return accountService.readAccount(login);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest accountRequest) {
		accountService.createAccount(accountRequest);
		String message = String.format("Account with id [ %s ] was created successfully", accountRequest.getLogin());
		return new ResponseEntity<String>(message, HttpStatus.CREATED);
	}
	
	@PatchMapping("/edit/{login}")
	public ResponseEntity<String> editAccount(@RequestBody EditAccountRequest accountRequest, @PathVariable(required = true) String login) {
		accountService.editAccount(login, accountRequest);
		String message = String.format("Account with id [ %s ] was edited successfully", login);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{login}")
	public ResponseEntity<String> deleteAccount(@PathVariable(required = true) String login) {
		accountService.deleteAccount(login);
		String message = String.format("Account with id [ %s ] was deleted successfully", login);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
}
