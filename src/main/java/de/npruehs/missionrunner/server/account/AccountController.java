package de.npruehs.missionrunner.server.account;

import java.util.Optional;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	private Random random;
	
	@Autowired
	private AccountRepository repository;
	
	@PostConstruct
	public void postConstruct() {
		random = new Random();
	}
	
	@RequestMapping("/account/get")
	public Account get(@RequestParam(value = "id") String id) {
		// Check if account exists.
		Optional<Account> existingAccount = repository.findById(id);
		
		if (existingAccount.isPresent()) {
			return existingAccount.get();
		}
		
		// Create new account (supports mobile auto-login to reduce user churns).
		String accountName = "Account" + random.nextInt(10000);
		Account newAccount = new Account(id, accountName, 1, 0);
		
		newAccount = repository.save(newAccount);
		return newAccount;
	}
}
