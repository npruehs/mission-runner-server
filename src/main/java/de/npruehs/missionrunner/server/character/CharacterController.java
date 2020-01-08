package de.npruehs.missionrunner.server.character;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharacterController {
	@RequestMapping("/characters/get")
	public Character[] get(@RequestParam(value = "accountId") String accountId) {
		CharacterSkill[] skills = new CharacterSkill[2];
		skills[0] = new CharacterSkill("A", 1);
		skills[1] = new CharacterSkill("B", 1);
		
		Character[] characters = new Character[2];
		characters[0] = new Character(0, accountId, "TestCharacterA", CharacterStatus.IDLE, 0, skills);
		characters[1] = new Character(1, accountId, "TestCharacterB", CharacterStatus.IDLE, 0, skills);
		
		return characters;
	}
}
