package de.npruehs.missionrunner.server.character;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.npruehs.missionrunner.server.ErrorCode;
import de.npruehs.missionrunner.server.NetworkResponse;
import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.account.AccountRepository;

@RestController
public class CharacterController {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CharacterRepository characterRepository;
	
	
	@GetMapping("/characters/get")
	public NetworkResponse<CharacterData[]> get(@RequestParam(value = "accountId") String accountId) {
		Optional<Account> account = accountRepository.findById(accountId);
		
		if (!account.isPresent()) {
			return NetworkResponse.newErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, "Account not found.");
		}
		
		List<Character> characters = characterRepository.findByAccount(account.get());
		CharacterData[] characterData = new CharacterData[characters.size()];

		// Convert character data.
		for (int i = 0; i < characters.size(); ++i) {
			characterData[i] = new CharacterData(characters.get(i));
		}
	
		return NetworkResponse.newSuccessResponse(characterData);
	}
}
