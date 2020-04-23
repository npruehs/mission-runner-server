package de.npruehs.missionrunner.server.mission;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
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
