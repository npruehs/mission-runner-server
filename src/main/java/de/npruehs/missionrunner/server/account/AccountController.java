package de.npruehs.missionrunner.server.account;

import java.util.Optional;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.character.CharacterSkill;
import de.npruehs.missionrunner.server.character.CharacterStatus;

@RestController
public class AccountController {
	private Random random;
	
	@Autowired
	private AccountRepository repository;
	
	@PostConstruct
	public void postConstruct() {
		random = new Random();
	}
	
	@GetMapping("/account/get")
	public Account get(@RequestParam(value = "id") String id) {
		// Check if account exists.
		Optional<Account> existingAccount = repository.findById(id);
		
		if (existingAccount.isPresent()) {
			return existingAccount.get();
		}
		
		// Create new account (supports mobile auto-login to reduce user churns).
		String accountName = "Account" + random.nextInt(10000);
		Account newAccount = new Account(id, accountName, 1, 0);
		
		// Create initial characters.
		CharacterSkill[] skills = new CharacterSkill[2];
		skills[0] = new CharacterSkill("A", 1);
		skills[1] = new CharacterSkill("B", 1);
		
		Character[] characters = new Character[2];
		characters[0] = new Character(newAccount, "TestCharacterA", CharacterStatus.IDLE, 0, skills);
		characters[1] = new Character(newAccount, "TestCharacterB", CharacterStatus.IDLE, 0, skills);
		
		// Save new account.
		newAccount = repository.save(newAccount);
		return newAccount;
	}
}
