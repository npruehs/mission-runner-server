package de.npruehs.missionrunner.server.mission.test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.npruehs.missionrunner.server.ErrorCode;
import de.npruehs.missionrunner.server.NetworkResponse;
import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.account.AccountRepository;
import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.character.CharacterRepository;
import de.npruehs.missionrunner.server.character.CharacterSkill;
import de.npruehs.missionrunner.server.character.CharacterStatus;
import de.npruehs.missionrunner.server.mission.Mission;
import de.npruehs.missionrunner.server.mission.MissionController;
import de.npruehs.missionrunner.server.mission.MissionData;
import de.npruehs.missionrunner.server.mission.MissionRepository;
import de.npruehs.missionrunner.server.mission.MissionRequirement;
import de.npruehs.missionrunner.server.mission.MissionStatus;
import de.npruehs.missionrunner.server.mission.net.FinishMissionRequest;
import de.npruehs.missionrunner.server.mission.net.FinishMissionResponse;
import de.npruehs.missionrunner.server.mission.net.StartMissionRequest;
import de.npruehs.missionrunner.server.mission.net.StartMissionResponse;

@WebMvcTest(controllers = MissionController.class)
public class MissionControllerTests {
	private enum TestMissionStatus {
		NOT_YET_FINISHED,
		ALREADY_FINISHED
	}
	
	private static final String RIGHT_CHARACTER_SKILL = "A";
	private static final String WRONG_CHARACTER_SKILL = "B";

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private AccountRepository accountRepository;
	
	@MockBean
	private MissionRepository missionRepository;
	
	@MockBean
	private CharacterRepository characterRepository;
	
	@Test
	public void getCalculatesRemainingTime() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		setupTestMission();

		// ACT.
		MvcResult mvcResult = mockMvc.perform(get("/missions/get")
				.param("accountId", testAccount.getId())
			    .contentType("application/json"))
			    .andExpect(status().isOk())
			    .andReturn();
		
		// ASSERT.
		String responseBody = mvcResult.getResponse().getContentAsString();
		  
		NetworkResponse<MissionData[]> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<MissionData[]>>() {});

		assertThat(response.getData()[0].getRemainingTime()).isGreaterThan(0);
	}
	
	@Test
	public void startChecksMissionStatus() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		Mission testMission = setupTestMission(MissionStatus.RUNNING);
		Character testCharacter = setupTestCharacter();
		
		// ACT.
		StartMissionRequest request = new StartMissionRequest(testAccount, testMission, testCharacter);
		String responseBody = postRequest("/missions/start", request);
		
		// ASSERT.
		NetworkResponse<StartMissionResponse> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<StartMissionResponse>>() {});

		assertThat(response.isSuccess()).isFalse();
		assertThat(response.getError().getErrorCode()).isEqualTo(ErrorCode.MISSION_ALREADY_RUNNING);
	}
	
	@Test
	public void startChecksMissionRequirements() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		Mission testMission = setupTestMission();
		Character testCharacter = setupTestCharacter(WRONG_CHARACTER_SKILL);
		
		// ACT.
		StartMissionRequest request = new StartMissionRequest(testAccount, testMission, testCharacter);
		String responseBody = postRequest("/missions/start", request);
		
		// ASSERT.
		NetworkResponse<StartMissionResponse> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<StartMissionResponse>>() {});

		assertThat(response.isSuccess()).isFalse();
		assertThat(response.getError().getErrorCode()).isEqualTo(ErrorCode.MISSION_REQUIREMENTS_NOT_MET);
	}
	
	@Test
	public void startSetsMissionStatus() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		Mission testMission = setupTestMission();
		Character testCharacter = setupTestCharacter();

		// ACT.
		StartMissionRequest request = new StartMissionRequest(testAccount, testMission, testCharacter);
		String responseBody = postRequest("/missions/start", request);
		
		// ASSERT.
		NetworkResponse<StartMissionResponse> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<StartMissionResponse>>() {});

		assertThat(response.isSuccess()).isTrue();
		assertThat(testMission.getStatus()).isEqualTo(MissionStatus.RUNNING);
		assertThat(response.getData().getMission().getStatus()).isEqualTo(MissionStatus.RUNNING);
	}
	
	@Test
	public void startChecksCharacterStatus() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		Mission testMission = setupTestMission();
		Character testCharacter = setupTestCharacter();
		
		testCharacter.setStatus(CharacterStatus.MISSION);
		
		// ACT.
		StartMissionRequest request = new StartMissionRequest(testAccount, testMission, testCharacter);
		String responseBody = postRequest("/missions/start", request);
		
		// ASSERT.
		NetworkResponse<StartMissionResponse> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<StartMissionResponse>>() {});

		assertThat(response.isSuccess()).isFalse();
		assertThat(response.getError().getErrorCode()).isEqualTo(ErrorCode.CHARACTER_NOT_IDLE);
	}
	
	@Test
	public void startAssignsCharacters() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		Mission testMission = setupTestMission();
		Character testCharacter = setupTestCharacter();
		
		// ACT.
		StartMissionRequest request = new StartMissionRequest(testAccount, testMission, testCharacter);
		String responseBody = postRequest("/missions/start", request);
		
		// ASSERT.
		NetworkResponse<StartMissionResponse> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<StartMissionResponse>>() {});

		assertThat(response.isSuccess()).isTrue();
		
		assertThat(testCharacter.getStatus()).isEqualTo(CharacterStatus.MISSION);
		assertThat(testCharacter.getMission().getId()).isEqualTo(testMission.getId());
		
		assertThat(response.getData().getCharacters()[0].getStatus()).isEqualTo(CharacterStatus.MISSION);
		assertThat(response.getData().getCharacters()[0].getMissionId()).isEqualTo(testMission.getId());
	}
	
	@Test
	public void finishChecksMissionStatus() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		Mission testMission = setupTestMission();

		// ACT.
		FinishMissionRequest request = new FinishMissionRequest(testAccount, testMission);
		String responseBody = postRequest("/missions/finish", request);
		
		// ASSERT.
		NetworkResponse<FinishMissionResponse> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<FinishMissionResponse>>() {});

		assertThat(response.isSuccess()).isFalse();
		assertThat(response.getError().getErrorCode()).isEqualTo(ErrorCode.MISSION_NOT_RUNNING);
	}
	
	@Test
	public void finishChecksMissionTime() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		Mission testMission = setupTestMission(MissionStatus.RUNNING);

		// ACT.
		FinishMissionRequest request = new FinishMissionRequest(testAccount, testMission);
		String responseBody = postRequest("/missions/finish", request);
		
		// ASSERT. 
		NetworkResponse<FinishMissionResponse> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<FinishMissionResponse>>() {});

		assertThat(response.isSuccess()).isFalse();
		assertThat(response.getError().getErrorCode()).isEqualTo(ErrorCode.MISSION_NOT_FINISHED_YET);
	}
	
	@Test
	public void finishGrantsMissionReward() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		Mission testMission = setupTestMission(MissionStatus.RUNNING, TestMissionStatus.ALREADY_FINISHED);
		setupTestCharacter();
		
		// ACT.
		FinishMissionRequest request = new FinishMissionRequest(testAccount, testMission);
		String responseBody = postRequest("/missions/finish", request);
		
		// ASSERT.
		NetworkResponse<FinishMissionResponse> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<FinishMissionResponse>>() {});

		assertThat(response.isSuccess()).isTrue();
		assertThat(testAccount.getScore()).isEqualTo(testMission.getReward());
		assertThat(response.getData().getAccount().getScore()).isEqualTo(testMission.getReward());
	}
	
	@Test
	public void finishGrantsLevelUp() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		Mission testMission = setupTestMission(MissionStatus.RUNNING, TestMissionStatus.ALREADY_FINISHED);
		Character testCharacter = setupTestCharacter(testMission);
		
		// ACT.
		FinishMissionRequest request = new FinishMissionRequest(testAccount, testMission);
		String responseBody = postRequest("/missions/finish", request);
		
		// ASSERT.
		NetworkResponse<FinishMissionResponse> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<FinishMissionResponse>>() {});

		assertThat(response.isSuccess()).isTrue();
		
		assertThat(testCharacter.getStatus()).isEqualTo(CharacterStatus.IDLE);
		assertThat(testCharacter.getMission()).isNull();
		
		assertThat(response.getData().getCharacters()[0].getStatus()).isEqualTo(CharacterStatus.IDLE);
		assertThat(response.getData().getCharacters()[0].getMissionId()).isNotEqualTo(testMission.getId());
	}
	
	@Test
	public void finishRemovesOldMission() throws Exception {
		// ARRANGE.
		Account testAccount = setupTestAccount();
		Mission testMission = setupTestMission(MissionStatus.RUNNING, TestMissionStatus.ALREADY_FINISHED);
		setupTestCharacter();
		
		// ACT.
		FinishMissionRequest request = new FinishMissionRequest(testAccount, testMission);
		String responseBody = postRequest("/missions/finish", request);
		
		// ASSERT.
		NetworkResponse<FinishMissionResponse> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<FinishMissionResponse>>() {});

		assertThat(response.isSuccess()).isTrue();
		assertThat(response.getData().getMissions().getRemovedMissions()).asList().contains(testMission.getId());
	}
	
	private Account setupTestAccount() {
		Account testAccount = new Account("A1B2C3", "TestAccount", 1, 0);
		
		Mockito.when(accountRepository.findById(testAccount.getId())).thenReturn(Optional.of(testAccount));
		
		return testAccount;
	}
	
	private Mission setupTestMission() {
		return setupTestMission(MissionStatus.OPEN);
	}
	
	private Mission setupTestMission(MissionStatus status) {
		return setupTestMission(status, TestMissionStatus.NOT_YET_FINISHED);
	}
	
	private Mission setupTestMission(MissionStatus status, TestMissionStatus testStatus) {
		int missionDurationSeconds = 10000;
		int missionElapsedTime = testStatus == TestMissionStatus.ALREADY_FINISHED ? missionDurationSeconds : (missionDurationSeconds / 2);
		
		DateTime missionStartDateTime = DateTime.now(DateTimeZone.UTC).minusSeconds(missionElapsedTime);
		Timestamp missionStartTimestamp = new Timestamp(missionStartDateTime.getMillis());
		
		MissionRequirement[] requirements = new MissionRequirement[] { new MissionRequirement(RIGHT_CHARACTER_SKILL, 1) };
		
		Mission testMission = new Mission(null, "TestMission", requirements, missionDurationSeconds, 0);
		testMission.setId(1);
		testMission.setStartTime(missionStartTimestamp);
		testMission.setStatus(status);
		
		List<Mission> testMissionList = new ArrayList<Mission>();
		testMissionList.add(testMission);
		
		Mockito.when(missionRepository.findByAccount(any())).thenReturn(testMissionList);
		Mockito.when(missionRepository.findById(testMission.getId())).thenReturn(Optional.of(testMission));
		
		return testMission;
	}
	
	private Character setupTestCharacter() {
		return setupTestCharacter(RIGHT_CHARACTER_SKILL, null);
	}
	
	private Character setupTestCharacter(String skill) {
		return setupTestCharacter(skill, null);
	}
	
	private Character setupTestCharacter(Mission assignedMission) {
		return setupTestCharacter(RIGHT_CHARACTER_SKILL, assignedMission);
	}
	
	private Character setupTestCharacter(String skill, Mission assignedMission) {
		CharacterSkill[] skills = new CharacterSkill[] { new CharacterSkill(skill, 1) };
		Character testCharacter = new Character(null, "TestCharacter", skills);
		
		List<Character> testCharacterList = new ArrayList<Character>();
		testCharacterList.add(testCharacter);
		
		Mockito.when(characterRepository.findAllById(any())).thenReturn(testCharacterList);
		
		if (assignedMission != null) {
			Mockito.when(characterRepository.findByMission(assignedMission)).thenReturn(testCharacterList);			
		}
		
		return testCharacter;
	}
	
	private String postRequest(String url, Object request) throws Exception {
		String content = objectMapper.writeValueAsString(request);
		
		MvcResult mvcResult = mockMvc.perform(post(url)
				.content(content)
			    .contentType("application/json"))
			    .andExpect(status().isOk())
			    .andReturn();
		
		String responseBody = mvcResult.getResponse().getContentAsString();
		return responseBody;
	}
}
