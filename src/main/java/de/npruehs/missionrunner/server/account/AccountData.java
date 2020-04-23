package de.npruehs.missionrunner.server.account;

import lombok.Getter;
import lombok.Setter;

public class AccountData {
	@Getter
	private String id;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private int level;
	
	@Getter
	@Setter
	private int score;
}
