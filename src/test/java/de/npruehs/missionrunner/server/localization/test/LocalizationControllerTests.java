package de.npruehs.missionrunner.server.localization.test;

import static com.google.common.truth.Truth.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.npruehs.missionrunner.server.localization.LocalizationController;
import de.npruehs.missionrunner.server.localization.LocalizationData;
import de.npruehs.missionrunner.server.localization.LocalizationRepository;
import de.npruehs.missionrunner.server.localization.LocalizedString;

@WebMvcTest(controllers = LocalizationController.class)
public class LocalizationControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private LocalizationRepository localizationRepository;
	
	@Test
	public void getCorrectHashReturnsEmptyList() throws Exception {
		// ARRANGE.
		// Build test strings.
		LocalizedString string = new LocalizedString();
		string.setId("id.test");
		string.setDe("Test");
		string.setEn("test");
		
		ArrayList<LocalizedString> strings = new ArrayList<LocalizedString>();
		strings.add(string);
		
		Mockito.when(localizationRepository.findAll()).thenReturn(strings);
		
		// ACT.
		// Get full localization.
		MvcResult mvcResult = mockMvc.perform(get("/localization/get")
				.param("hash", "")
			    .contentType("application/json"))
			    .andExpect(status().isOk())
			    .andReturn();
		
		String responseBody = mvcResult.getResponse().getContentAsString();
		  
		LocalizationData localizationData = objectMapper.readValue(responseBody, LocalizationData.class);
		String localizationHash = localizationData.getHash();
		
		// Request localization with matching hash.
		mvcResult = mockMvc.perform(get("/localization/get")
				.param("hash", localizationHash)
			    .contentType("application/json"))
			    .andExpect(status().isOk())
			    .andReturn();
		
		responseBody = mvcResult.getResponse().getContentAsString();
		
		localizationData = objectMapper.readValue(responseBody, LocalizationData.class);

		// ASSERT.
		assertThat(localizationData.getStrings()).isNull();
	}
}
