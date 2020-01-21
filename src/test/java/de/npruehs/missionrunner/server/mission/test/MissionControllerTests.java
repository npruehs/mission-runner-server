package de.npruehs.missionrunner.server.mission.test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import de.npruehs.missionrunner.server.NetworkResponse;
import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.account.AccountRepository;
import de.npruehs.missionrunner.server.character.CharacterRepository;
import de.npruehs.missionrunner.server.mission.Mission;
import de.npruehs.missionrunner.server.mission.MissionController;
import de.npruehs.missionrunner.server.mission.MissionRepository;

@WebMvcTest(controllers = MissionController.class)
public class MissionControllerTests {
	private static final String ACCOUNT_ID = "A1B2C3";
	
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
		setupMockAccount();

		// Setup mock mission.
		int missionDurationSeconds = 100;
		int missionElapsedTime = 3;
		
		DateTime missionStartDateTime = DateTime.now(DateTimeZone.UTC).minusSeconds(missionElapsedTime);
		Timestamp missionStartTimestamp = new Timestamp(missionStartDateTime.getMillis());
		
		Mission mockMission = new Mission(null, "TestMission", null, missionDurationSeconds, 0);
		mockMission.setStartTime(missionStartTimestamp);
		
		List<Mission> mockMissionList = new ArrayList<Mission>();
		mockMissionList.add(mockMission);
		
		Mockito.when(missionRepository.findByAccount(any())).thenReturn(mockMissionList);
		
		// ACT.
		MvcResult mvcResult = mockMvc.perform(get("/missions/get")
				.param("accountId", ACCOUNT_ID)
			    .contentType("application/json"))
			    .andExpect(status().isOk())
			    .andReturn();
		
		// ASSERT.
		String responseBody = mvcResult.getResponse().getContentAsString();
		  
		NetworkResponse<Mission[]> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<Mission[]>>() {});

		assertThat(response.getData()[0].getRemainingTime()).isGreaterThan(0);
	}
	
	private void setupMockAccount() {
		Account mockAccount = new Account(ACCOUNT_ID, "TestAccount", 1, 0);
		Optional<Account> optionalMockAccount = Optional.of(mockAccount);
		
		Mockito.when(accountRepository.findById(ACCOUNT_ID)).thenReturn(optionalMockAccount);
	}
}
