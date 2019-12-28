package de.npruehs.missionrunner.server.account;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	@RequestMapping("/account/get")
	public Account get(@RequestParam(value = "id") String id) {
		return new Account(id, 3, 1400);
	}
}
