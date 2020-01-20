package de.npruehs.missionrunner.server.mission;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.npruehs.missionrunner.server.ErrorCode;
import de.npruehs.missionrunner.server.NetworkResponse;
import de.npruehs.missionrunner.server.character.CharacterSkill;
import de.npruehs.missionrunner.server.character.CharacterStatus;
import de.npruehs.missionrunner.server.mission.net.FinishMissionRequest;
import de.npruehs.missionrunner.server.mission.net.FinishMissionResponse;
import de.npruehs.missionrunner.server.mission.net.StartMissionRequest;
import de.npruehs.missionrunner.server.mission.net.StartMissionResponse;

@RestController
public class MissionController {
	@GetMapping("/missions/get")
	public Mission[] get(@RequestParam(value = "accountId") String accountId) {
		MissionRequirement[] requirements = new MissionRequirement[2];
		requirements[0] = new MissionRequirement("A", 2);
		requirements[1] = new MissionRequirement("B", 1);
		
		Mission[] missions = new Mission[2];
		missions[0] = new Mission(0, accountId, "TestMissionA", MissionStatus.OPEN, requirements, 10, 10, 100);
		missions[1] = new Mission(1, accountId, "TestMissionB", MissionStatus.OPEN, requirements, 10, 10, 100);
		
		return missions;
	}
	
	@PostMapping("/missions/start")
	public NetworkResponse<StartMissionResponse> start(@RequestBody StartMissionRequest request) {
		if (request == null) {
			return NetworkResponse.newErrorResponse(ErrorCode.BAD_REQUEST, "Bad Request");
		}
		
		// TODO(np): Check if requirements are met.
		
		StartMissionResponse.MissionUpdate mission = new StartMissionResponse.MissionUpdate();
		mission.setId(request.getMissionId());
		mission.setStatus(MissionStatus.RUNNING);
		
		StartMissionResponse.CharacterUpdate[] characters = new StartMissionResponse.CharacterUpdate[request.getCharacterIds().length];
		
		for (int i = 0; i < request.getCharacterIds().length; ++i) {
			StartMissionResponse.CharacterUpdate character = new StartMissionResponse.CharacterUpdate();
			character.setId(request.getCharacterIds()[i]);
			character.setMissionId(request.getMissionId());
			character.setStatus(CharacterStatus.MISSION);
			
			characters[i] = character;
		}
		
		StartMissionResponse response = new StartMissionResponse();
		response.setAccountId(request.getAccountId());
		response.setMission(mission);
		response.setCharacters(characters);
		
		return NetworkResponse.newSuccessResponse(response);
	}
	
	@PostMapping("/missions/finish")
	public NetworkResponse<FinishMissionResponse> start(@RequestBody FinishMissionRequest request) {
		if (request == null) {
			return NetworkResponse.newErrorResponse(ErrorCode.BAD_REQUEST, "Bad Request");
		}
		
		// TODO(np): Check if mission is actually finished.
		
		FinishMissionResponse.AccountUpdate account = new FinishMissionResponse.AccountUpdate();
		account.setLevel(4);
		account.setScore(1500);
		
		MissionRequirement[] requirements = new MissionRequirement[2];
		requirements[0] = new MissionRequirement("A", 3);
		requirements[1] = new MissionRequirement("B", 2);
		
		Mission[] newMissions = new Mission[2];
		newMissions[0] = new Mission(2, request.getAccountId(), "TestMissionC", MissionStatus.OPEN, requirements, 20, 20, 200);
		newMissions[1] = new Mission(3, request.getAccountId(), "TestMissionD", MissionStatus.OPEN, requirements, 20, 20, 200);
		
		FinishMissionResponse.MissionUpdate missions = new FinishMissionResponse.MissionUpdate();
		missions.setRemovedMissions(new int[] { request.getMissionId() });
		missions.setAddedMissions(newMissions);

		FinishMissionResponse.CharacterUpdate[] characters = new FinishMissionResponse.CharacterUpdate[3];
		
		for (int i = 0; i < 2; ++i) {
			FinishMissionResponse.CharacterUpdate character = new FinishMissionResponse.CharacterUpdate();
			character.setId(i);
			character.setMissionId(0);
			character.setStatus(CharacterStatus.IDLE);
			characters[i] = character;
		}
		
		// Add new unlocked character.
		CharacterSkill[] skills = new CharacterSkill[1];
		skills[0] = new CharacterSkill("C", 1);
		
		characters[2] = new FinishMissionResponse.CharacterUpdate();
		characters[2].setId(2);
		characters[2].setName("TestCharacterC");
		characters[2].setStatus(CharacterStatus.IDLE);
		characters[2].setMissionId(0);
		characters[2].setSkills(skills);

		FinishMissionResponse response = new FinishMissionResponse();
		response.setAccount(account);
		response.setMissions(missions);
		response.setCharacters(characters);
		
		return NetworkResponse.newSuccessResponse(response);
	}
}
