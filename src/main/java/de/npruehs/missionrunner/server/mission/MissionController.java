package de.npruehs.missionrunner.server.mission;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.npruehs.missionrunner.server.NetworkResponse;
import de.npruehs.missionrunner.server.character.CharacterStatus;
import de.npruehs.missionrunner.server.mission.net.StartMissionRequest;
import de.npruehs.missionrunner.server.mission.net.StartMissionResponse;

@RestController
public class MissionController {
	private static int ERROR_BAD_REQUEST = 1;
	
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
			return NetworkResponse.newErrorResponse(ERROR_BAD_REQUEST, "Bad Request");
		}
		
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
}
