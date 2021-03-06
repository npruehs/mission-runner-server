package de.npruehs.missionrunner.server.account;

import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import de.npruehs.missionrunner.server.analytics.AnalyticsEventSender;
import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.character.CharacterSkill;
import de.npruehs.missionrunner.server.mission.Mission;
import de.npruehs.missionrunner.server.mission.MissionRequirement;

@RestController
public class AccountController {
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	private Random random;
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private AnalyticsEventSender analyticsEventSender;
	
	@GetMapping("/account/get")
	public AccountData get(@RequestParam(value = "id") String id) {
		// Check if account exists.
		Optional<Account> existingAccount = repository.findById(id);
		
		if (existingAccount.isPresent()) {
			return modelMapper.map(existingAccount.get(), AccountData.class);
		}
		
		// Create new account (supports mobile auto-login to reduce user churns).
		String accountName = "Account" + random.nextInt(10000);
		Account newAccount = new Account(id, accountName, 1, 0);
		
		// Create initial characters.
		CharacterSkill[] skills = new CharacterSkill[1];
		skills[0] = new CharacterSkill("skill.A", 1);
		
		Character[] characters = new Character[1];
		characters[0] = new Character(newAccount, "character.tutorial", skills);

		// Create initial missions.
		MissionRequirement[] requirements = new MissionRequirement[1];
		requirements[0] = new MissionRequirement("skill.A", 1);
		
		Mission[] missions = new Mission[1];
		missions[0] = new Mission(newAccount, "mission.tutorial", requirements, 10, 100);
		
		// Save new account.
		newAccount.setCharacters(Lists.newArrayList(characters));
		newAccount.setMissions(Lists.newArrayList(missions));
		
		newAccount = repository.save(newAccount);
		
		// Send analytics event.
		analyticsEventSender.sendEvent("account:created");
		
		return modelMapper.map(newAccount, AccountData.class);
	}
}
