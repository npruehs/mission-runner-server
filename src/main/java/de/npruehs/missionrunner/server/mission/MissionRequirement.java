package de.npruehs.missionrunner.server.mission;

public class MissionRequirement {
	private final String requirement;
	
	private final int count;
	
	public MissionRequirement(String requirement, int count) {
		this.requirement = requirement;
		this.count = count;
	}

	public String getRequirement() {
		return requirement;
	}

	public int getCount() {
		return count;
	}
}
