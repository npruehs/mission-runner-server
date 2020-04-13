package de.npruehs.missionrunner.server.account.test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.account.AccountController;
import de.npruehs.missionrunner.server.account.AccountData;
import de.npruehs.missionrunner.server.account.AccountRepository;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private AccountRepository accountRepository;
	
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
			RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentation)) 
				.build();
	}
	
	@Test
	public void getCreatesNewAccount() throws Exception {
		// ARRANGE.
		final String accountId = "A1B2C3";
		
		Mockito.when(accountRepository.save(any())).thenReturn(new Account(accountId, "", 1, 0));
		
		// ACT.
		MvcResult mvcResult = mockMvc.perform(get("/account/get")
				.param("id", accountId)
			    .contentType("application/json"))
			    .andExpect(status().isOk())
			    .andDo(document("/account/get",
			    		requestParameters( 
			    				parameterWithName("id").description("Id of the account to get.") 
			    		),
			    		responseFields( 
							fieldWithPath("id").description("Id of the account whose data was returned."), 
							fieldWithPath("name").description("User-specified name of the account."),
							fieldWithPath("level").description("Current account level. Higher means access to more features."),
							fieldWithPath("score").description("Current account score. Higher means closer to next account level."))))
			    .andReturn();
		
		// ASSERT.
		String responseBody = mvcResult.getResponse().getContentAsString();
		  
		AccountData account = objectMapper.readValue(responseBody, AccountData.class);

		assertThat(account.getId()).isEqualTo(accountId);
	}
	
	@Test
	public void getReturnsExistingAccount() throws Exception {
		// ARRANGE.
		final String accountId = "A1B2C3";
		
		Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(new Account(accountId, "", 1, 0)));
		
		// ACT.
		MvcResult mvcResult = mockMvc.perform(get("/account/get")
				.param("id", accountId)
			    .contentType("application/json"))
			    .andExpect(status().isOk())
			    .andReturn();
		
		// ASSERT.
		String responseBody = mvcResult.getResponse().getContentAsString();
		  
		AccountData account = objectMapper.readValue(responseBody, AccountData.class);

		assertThat(account.getId()).isEqualTo(accountId);
	}
}
