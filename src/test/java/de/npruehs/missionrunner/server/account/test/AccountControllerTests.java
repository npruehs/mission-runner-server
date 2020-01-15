package de.npruehs.missionrunner.server.account.test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.account.AccountController;
import de.npruehs.missionrunner.server.account.AccountRepository;

@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private AccountRepository accountRepository;
	
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
			    .andReturn();
		
		// ASSERT.
		String responseBody = mvcResult.getResponse().getContentAsString();
		  
		Account account = objectMapper.readValue(responseBody, Account.class);

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
		  
		Account account = objectMapper.readValue(responseBody, Account.class);

		assertThat(account.getId()).isEqualTo(accountId);
	}
}
