package de.npruehs.missionrunner.server.mission;

import lombok.Getter;

public class MissionRequirement {
	@Getter
	private String requirement;
	
	@Getter
	private int count;
	
	public MissionRequirement() {
	}
	
	public MissionRequirement(String requirement, int count) {
		this.requirement = requirement;
		this.count = count;
	}
}
