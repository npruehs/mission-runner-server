package de.npruehs.missionrunner.server.test;

import static com.google.common.truth.Truth.assertThat;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.npruehs.missionrunner.server.Gameplay;
import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.character.CharacterSkill;
import de.npruehs.missionrunner.server.mission.Mission;
import de.npruehs.missionrunner.server.mission.MissionRequirement;

public class GameplayTests {
	private static Gameplay gameplay;
	
	@BeforeAll
	public static void beforeAll() {
		gameplay = new Gameplay(new Random());
	}
	
	@Test
	public void canStartMissionReturnsTrue() {
		// ARRANGE.
		MissionRequirement[] requirements = new MissionRequirement[2];
		requirements[0] = new MissionRequirement("A", 2);
		requirements[1] = new MissionRequirement("B", 1);
		
		Mission mission = new Mission(null, "TestMission", requirements, 0, 0);
	
		CharacterSkill[] skills = new CharacterSkill[2];
		skills[0] = new CharacterSkill("A", 1);
		skills[1] = new CharacterSkill("B", 1);
		
		Character[] characters = new Character[2];
		characters[0] = new Character(null, "TestCharacter1", skills);
		characters[1] = new Character(null, "TestCharacter2", skills);
		
		// ACT.
		boolean canStartMission = gameplay.canStartMission(mission, Arrays.asList(characters));
		
		// ASSERT.
		assertThat(canStartMission);
	}
	
	@Test
	public void canStartMissionReturnsFalse() {
		// ARRANGE.
		MissionRequirement[] requirements = new MissionRequirement[2];
		requirements[0] = new MissionRequirement("A", 2);
		requirements[1] = new MissionRequirement("B", 3);
		
		Mission mission = new Mission(null, "TestMission", requirements, 0, 0);
	
		CharacterSkill[] skills = new CharacterSkill[2];
		skills[0] = new CharacterSkill("A", 1);
		skills[1] = new CharacterSkill("B", 1);
		
		Character[] characters = new Character[2];
		characters[0] = new Character(null, "TestCharacter1", skills);
		characters[1] = new Character(null, "TestCharacter2", skills);
		
		// ACT.
		boolean canStartMission = gameplay.canStartMission(mission, Arrays.asList(characters));
		
		// ASSERT.
		assertThat(canStartMission).isFalse();
	}
	
	@Test
	public void accountLevelIncreasesWithScore() {
		// ACT.
		int levelForScore0 = gameplay.getAccountLevel(0);
		int levelForScore100 = gameplay.getAccountLevel(100);
		int levelForScore300 = gameplay.getAccountLevel(300);
		int levelForScore600 = gameplay.getAccountLevel(600);
		int levelForScore1000 = gameplay.getAccountLevel(1000);
		
		// ASSERT.
		assertThat(levelForScore100).isGreaterThan(levelForScore0);
		assertThat(levelForScore300).isGreaterThan(levelForScore100);
		assertThat(levelForScore600).isGreaterThan(levelForScore300);
		assertThat(levelForScore1000).isGreaterThan(levelForScore600);
	}
	
	@Test
	public void charactersAtHigherLevelsHaveDifferentPrimarySkills() {
		// ACT.
		Character level2Character = gameplay.getNewCharacterAtLevel(null, 2);
		Character level5Character = gameplay.getNewCharacterAtLevel(null, 5);
		
		// ASSERT.
		assertThat(level2Character.getSkills()[0].getSkill()).isNotEqualTo(level5Character.getSkills()[0].getSkill());
	}
	
	@Test
	public void missionsAtHigherLevelsTakeLonger() {
		// ACT.
		Mission level2Mission = gameplay.getMissionAtLevel(null, 2);
		Mission level5Mission = gameplay.getMissionAtLevel(null, 5);
		
		// ASSERT.
		assertThat(level5Mission.getRequiredTime()).isGreaterThan(level2Mission.getRequiredTime());
	}
}
