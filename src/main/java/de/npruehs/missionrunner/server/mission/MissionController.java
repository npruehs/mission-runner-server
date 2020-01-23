package de.npruehs.missionrunner.server.mission;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Iterables;

import de.npruehs.missionrunner.server.ErrorCode;
import de.npruehs.missionrunner.server.Gameplay;
import de.npruehs.missionrunner.server.NetworkResponse;
import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.account.AccountRepository;
import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.character.CharacterRepository;
import de.npruehs.missionrunner.server.character.CharacterStatus;
import de.npruehs.missionrunner.server.mission.net.FinishMissionRequest;
import de.npruehs.missionrunner.server.mission.net.FinishMissionResponse;
import de.npruehs.missionrunner.server.mission.net.StartMissionRequest;
import de.npruehs.missionrunner.server.mission.net.StartMissionResponse;

@RestController
public class MissionController {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MissionRepository missionRepository;

	@Autowired
	private CharacterRepository characterRepository;

	@Autowired
	private Gameplay gameplay;
	
	@GetMapping("/missions/get")
	public NetworkResponse<Mission[]> get(@RequestParam(value = "accountId") String accountId) {
		Optional<Account> account = accountRepository.findById(accountId);

		if (!account.isPresent()) {
			return NetworkResponse.newErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, "Account not found.");
		}

		List<Mission> missions = missionRepository.findByAccount(account.get());
		Mission[] missionsArray = Iterables.toArray(missions, Mission.class);

		// Convert remaining time.
		for (Mission mission : missionsArray) {
			if (mission.getStartTime() != null) {
				DateTime missionStartTime = new DateTime(mission.getStartTime().getTime());
				DateTime missionEndTime = missionStartTime.plusSeconds(mission.getRequiredTime());
				DateTime now = DateTime.now(DateTimeZone.UTC);
				
				if (missionEndTime.isBefore(now)) {
					mission.setRemainingTime(0);
				} else {
					Period remainingTime = new Period(now, missionEndTime, PeriodType.seconds());
					mission.setRemainingTime(remainingTime.getSeconds());
				}
			}
		}
		
		return NetworkResponse.newSuccessResponse(missionsArray);
	}

	@PostMapping("/missions/start")
	public NetworkResponse<StartMissionResponse> start(@RequestBody StartMissionRequest request) {
		if (request == null) {
			return NetworkResponse.newErrorResponse(ErrorCode.BAD_REQUEST, "Bad Request");
		}
		
		// Find mission.
		Optional<Mission> mission = missionRepository.findById(request.getMissionId());

		if (!mission.isPresent()) {
			return NetworkResponse.newErrorResponse(ErrorCode.MISSION_NOT_FOUND, "Mission not found.");
		}
		
		// Check mission status.
		if (mission.get().getStatus() != MissionStatus.OPEN) {
			return NetworkResponse.newErrorResponse(ErrorCode.MISSION_ALREADY_RUNNING, "Mission already running.");
		}
				
		// Find characters.
		Iterable<Character> characters = characterRepository.findAllById(Arrays.asList(request.getCharacterIds()));
		
		// Check character status.
		for (Character character : characters) {
			if (character.getStatus() != CharacterStatus.IDLE) {
				return NetworkResponse.newErrorResponse(ErrorCode.CHARACTER_NOT_IDLE, "Character not idle.");
			}
		}

		// Check mission requirements.
		if (!gameplay.canStartMission(mission.get(), characters)) {
			return NetworkResponse.newErrorResponse(ErrorCode.MISSION_REQUIREMENTS_NOT_MET, "Mission requirements not met.");
		}
		
		// Start mission.
		mission.get().setStatus(MissionStatus.RUNNING);
		mission.get().setStartTime(new Timestamp(DateTime.now(DateTimeZone.UTC).getMillis()));
		missionRepository.save(mission.get());
		
		StartMissionResponse.MissionUpdate missionUpdate = new StartMissionResponse.MissionUpdate();
		missionUpdate.setId(request.getMissionId());
		missionUpdate.setStatus(MissionStatus.RUNNING);

		// Assign characters.
		for (Character character : characters) {
			character.setMission(mission.get());
			character.setStatus(CharacterStatus.MISSION);
		}
		
		characterRepository.saveAll(characters);
		
		StartMissionResponse.CharacterUpdate[] characterUpdates = new StartMissionResponse.CharacterUpdate[request.getCharacterIds().length];
		
		for (int i = 0; i < request.getCharacterIds().length; ++i) {
			StartMissionResponse.CharacterUpdate characterUpdate = new StartMissionResponse.CharacterUpdate();
			characterUpdate.setId(request.getCharacterIds()[i]);
			characterUpdate.setMissionId(request.getMissionId());
			characterUpdate.setStatus(CharacterStatus.MISSION);
			
			characterUpdates[i] = characterUpdate;
		}
		
		// Send response.
		StartMissionResponse response = new StartMissionResponse();
		response.setAccountId(request.getAccountId());
		response.setMission(missionUpdate);
		response.setCharacters(characterUpdates);
		
		return NetworkResponse.newSuccessResponse(response);
	}

	@PostMapping("/missions/finish")
	public NetworkResponse<FinishMissionResponse> finish(@RequestBody FinishMissionRequest request) {
		if (request == null) {
			return NetworkResponse.newErrorResponse(ErrorCode.BAD_REQUEST, "Bad Request");
		}

		// Find account.
		Optional<Account> account = accountRepository.findById(request.getAccountId());

		if (!account.isPresent()) {
			return NetworkResponse.newErrorResponse(ErrorCode.ACCOUNT_NOT_FOUND, "Account not found.");
		}
		
		// Find mission.
		Optional<Mission> mission = missionRepository.findById(request.getMissionId());

		if (!mission.isPresent()) {
			return NetworkResponse.newErrorResponse(ErrorCode.MISSION_NOT_FOUND, "Mission not found.");
		}

		// Check mission status.
		if (mission.get().getStatus() != MissionStatus.RUNNING) {
			return NetworkResponse.newErrorResponse(ErrorCode.MISSION_NOT_RUNNING, "Mission not running.");
		}
				
		// Check if mission is actually finished.
		DateTime missionStartTime = new DateTime(mission.get().getStartTime().getTime());
		DateTime missionEndTime = missionStartTime.plusSeconds(mission.get().getRequiredTime());
		
		DateTime now = DateTime.now(DateTimeZone.UTC);
		
		if (missionEndTime.isAfter(now)) {
			return NetworkResponse.newErrorResponse(ErrorCode.MISSION_NOT_FINISHED_YET, "Mission not finished yet.");
		}
		
		// Grant reward.
		int oldScore = account.get().getScore();
		int newScore = oldScore + mission.get().getReward();
		
		account.get().setScore(newScore);
		
		// Check for level-up.
		int oldLevel = gameplay.getAccountLevel(oldScore);
		int newLevel = gameplay.getAccountLevel(newScore);
		
		if (newLevel > oldLevel) {
			account.get().setLevel(newLevel);
		}
		
		accountRepository.save(account.get());
		
		// Free characters.
		Iterable<Character> assignedCharacters = characterRepository.findByMission(mission.get());
		
		for (Character assignedCharacter : assignedCharacters) {
			assignedCharacter.setMission(null);
			assignedCharacter.setStatus(CharacterStatus.IDLE);
		}

		characterRepository.saveAll(assignedCharacters);
		
		// Generate new character at level up.
		Character newCharacter = null;
		
		if (newLevel > oldLevel) {
			newCharacter = gameplay.getNewCharacterAtLevel(account.get(), newLevel);
			characterRepository.save(newCharacter);
		}
		
		// Remove old mission.
		missionRepository.delete(mission.get());
		
		// Generate new mission.
		Mission newMission = gameplay.getMissionAtLevel(account.get(), newLevel);
		missionRepository.save(newMission);
		
		// Build account update.
		FinishMissionResponse.AccountUpdate accountUpdate = new FinishMissionResponse.AccountUpdate();
		accountUpdate.setLevel(newLevel);
		accountUpdate.setScore(newScore);

		// Build mission update.
		FinishMissionResponse.MissionUpdate missionUpdate = new FinishMissionResponse.MissionUpdate();
		missionUpdate.setRemovedMissions(new long[] { request.getMissionId() });
		missionUpdate.setAddedMissions(new Mission[] { newMission });

		// Build unassigned characters update.
		int changedCharacters = Iterables.size(assignedCharacters);
		
		if (newCharacter != null) {
			++changedCharacters;
		}
		
		FinishMissionResponse.CharacterUpdate[] characterUpdates = new FinishMissionResponse.CharacterUpdate[changedCharacters];
		int characterIndex = 0;
		
		for (Character character : assignedCharacters) {
			FinishMissionResponse.CharacterUpdate characterUpdate = new FinishMissionResponse.CharacterUpdate();
			characterUpdate.setId(character.getId());
			characterUpdate.setMissionId(0);
			characterUpdate.setStatus(CharacterStatus.IDLE);
			characterUpdates[characterIndex] = characterUpdate;
			
			++characterIndex;
		}

		// Build new character update.
		if (newCharacter != null) {
			characterUpdates[characterIndex] = new FinishMissionResponse.CharacterUpdate();
			characterUpdates[characterIndex].setId(newCharacter.getId());
			characterUpdates[characterIndex].setName(newCharacter.getName());
			characterUpdates[characterIndex].setStatus(newCharacter.getStatus());
			characterUpdates[characterIndex].setMissionId(0);
			characterUpdates[characterIndex].setSkills(newCharacter.getSkills());
		}

		// Return response.
		FinishMissionResponse response = new FinishMissionResponse();
		response.setAccount(accountUpdate);
		response.setMissions(missionUpdate);
		response.setCharacters(characterUpdates);

		return NetworkResponse.newSuccessResponse(response);
	}
}
