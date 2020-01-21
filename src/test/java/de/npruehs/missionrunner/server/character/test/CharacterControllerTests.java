package de.npruehs.missionrunner.server.character.test;

import static com.google.common.truth.Truth.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.character.CharacterController;
import de.npruehs.missionrunner.server.character.CharacterRepository;

@WebMvcTest(controllers = CharacterController.class)
public class CharacterControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private AccountRepository accountRepository;
	
	@MockBean
	private CharacterRepository characterRepository;
	
	@Test
	public void getReturnsAssociatedCharacters() throws Exception {
		// ARRANGE.
		final String accountId = "A1B2C3";
		
		Account mockAccount = new Account(accountId, "", 1, 0);
		Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
		
		List<Character> mockCharacters = new ArrayList<Character>();
		Character mockCharacter = new Character(mockAccount, "TestCharacter", null);
		mockCharacters.add(mockCharacter);
		
		Mockito.when(characterRepository.findByAccount(mockAccount)).thenReturn(mockCharacters);
		
		// ACT.
		MvcResult mvcResult = mockMvc.perform(get("/characters/get")
				.param("accountId", accountId)
			    .contentType("application/json"))
			    .andExpect(status().isOk())
			    .andReturn();
		
		// ASSERT.
		String responseBody = mvcResult.getResponse().getContentAsString();
		  
		NetworkResponse<Character[]> response = objectMapper.readValue(responseBody, new TypeReference<NetworkResponse<Character[]>>() {});
		
		assertThat(response.getData()[0].getAccount().getId()).isEqualTo(accountId);
		assertThat(response.getData()[0].getId()).isEqualTo(mockCharacter.getId());
	}
}
