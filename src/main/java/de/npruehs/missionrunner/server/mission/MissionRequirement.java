package de.npruehs.missionrunner.server.mission;

public class MissionRequirement {
	private String requirement;
	
	private int count;
	
	public MissionRequirement() {
	}
	
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

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
