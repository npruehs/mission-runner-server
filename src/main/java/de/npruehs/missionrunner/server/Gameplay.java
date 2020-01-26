package de.npruehs.missionrunner.server;

import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.character.CharacterSkill;
import de.npruehs.missionrunner.server.mission.Mission;
import de.npruehs.missionrunner.server.mission.MissionRequirement;

public class Gameplay {
	private static String[] characterSkills = new String[]
			{ "skill.A", "skill.B", "skill.C", "skill.D", "skill.E", "skill.F", "skill.G", "skill.H" };
	
	@Autowired
	private Random random;
	
	public Gameplay() {
	}
	
	public Gameplay(Random random) {
		this.random = random;
	}
	
	public boolean canStartMission(Mission mission, Iterable<Character> assignedCharacters) {
		// Get mission requirements.
		if (mission == null) {
			return false;
		}

		HashMap<String, Integer> requirements = new HashMap<>();

		for (MissionRequirement requirement : mission.getRequirements()) {
			requirements.put(requirement.getRequirement(), requirement.getCount());
		}

		// Apply character skills.
		for (Character character : assignedCharacters) {
			for (CharacterSkill skill : character.getSkills()) {
				if (requirements.containsKey(skill.getSkill())) {
					Integer requiredSkills = requirements.get(skill.getSkill());

					if (requiredSkills > 0) {
						requiredSkills -= skill.getCount();
						requirements.put(skill.getSkill(), requiredSkills);
					}
				}
			}
		}

		// Check if all requirements are met.
		for (Integer requiredSkills : requirements.values()) {
			if (requiredSkills > 0) {
				return false;
			}
		}

		return true;
	}
	
	public int getAccountLevel(int score) {
		// We want players to require an increasing score for level ups.
		// This will closely resemble the Gaussian sum times 100, e.g.
		//  100 = Level 2
		//  300 = Level 3
		//  600 = Level 4
		// 1000 = Level 5
		return (int)(Math.floor(0.5 + Math.sqrt(0.25 + (score / 50.0))));
	}
	
	public Character getNewCharacterAtLevel(Account account, int level) {
		// Generate a character with a new skill every level (while there is).
		int firstSkillIndex = (level - 1) % characterSkills.length;

		// Add a random second skill that the player might have seen before.
		int maxSkillIndex = Math.min(level - 1, characterSkills.length - 1);
		int secondSkillIndex = random.nextInt(Math.max(maxSkillIndex, 1));
		
		CharacterSkill[] skills;
		
		if (firstSkillIndex != secondSkillIndex) {
			skills = new CharacterSkill[2];
			skills[0] = new CharacterSkill(characterSkills[firstSkillIndex], 1);
			skills[1] = new CharacterSkill(characterSkills[secondSkillIndex], 1);
		} else {
			skills = new CharacterSkill[1];
			skills[0] = new CharacterSkill(characterSkills[firstSkillIndex], 2);
		}
		
		Character newCharacter = new Character(account, "character.awesomenewcharacter" + level, skills);
		return newCharacter;
	}
	
	public Mission getMissionAtLevel(Account account, int level) {
		// Build requirements.
		int requirementCount = Math.max(2, level / 2);
		
		HashMap<String, Integer> requirements = new HashMap<>();

		for (int i = 0; i < requirementCount; ++i) {
			int maxSkillIndex = Math.min(level - 1, characterSkills.length - 1);
			int skillIndex = random.nextInt(Math.max(maxSkillIndex, 1));
			
			String newRequirement = characterSkills[skillIndex];

			if (!requirements.containsKey(newRequirement)) {
				requirements.put(newRequirement, 1);
			} else {
				Integer requiredSkills = requirements.get(newRequirement);
				++requiredSkills;
				requirements.put(newRequirement, requiredSkills);
			}
		}
		
		MissionRequirement[] requirementsArray = new MissionRequirement[requirements.size()];
		int requirementIndex = 0;
		
		for (String requirement : requirements.keySet()) {
			requirementsArray[requirementIndex] = new MissionRequirement(requirement, requirements.get(requirement));
			++requirementIndex;
		}
		
		// Scale rewards with level.
		int requiredTime = level * (level + 1) * 5;
		int reward = (int)Math.ceil(requiredTime / 1.5);
		
		Mission newMission = new Mission(account, "mission.superimportantmission" + random.nextInt(100),
				requirementsArray, requiredTime, reward);
		
		return newMission;
	}
}
