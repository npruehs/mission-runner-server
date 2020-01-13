package de.npruehs.missionrunner.server.account;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	private Random random;
	
	@PostConstruct
	public void postConstruct() {
		random = new Random();
	}
	
	@RequestMapping("/account/get")
	public Account get(@RequestParam(value = "id") String id) {
		String accountName = "Account" + random.nextInt(10000);
		return new Account(id, accountName, 3, 1400);
	}
}
