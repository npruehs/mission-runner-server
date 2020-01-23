package de.npruehs.missionrunner.server.mission;

import java.io.Serializable;

public class MissionRequirement implements Serializable {
	private static final long serialVersionUID = 5930616380135034694L;

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
