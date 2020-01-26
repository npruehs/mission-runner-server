package de.npruehs.missionrunner.server.mission;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class MissionData {
	private long id;
    private String name;
    private MissionStatus status;
    private MissionRequirement[] requirements;
    private int requiredTime;
    private int remainingTime;
    private int reward;
    
    public MissionData() {
    }
    
    public MissionData(Mission mission) {
    	this.id = mission.getId();
    	this.name = mission.getName();
    	this.status = mission.getStatus();
    	this.requirements = mission.getRequirements();
    	this.requiredTime = mission.getRequiredTime();
    	this.reward = mission.getReward();
    	
    	// Set remaining time.
    	if (mission.getStartTime() == null) {
    		remainingTime = requiredTime;
		} else {

			DateTime missionStartTime = new DateTime(mission.getStartTime().getTime());
			DateTime missionEndTime = missionStartTime.plusSeconds(mission.getRequiredTime());
			DateTime now = DateTime.now(DateTimeZone.UTC);
			
			if (missionEndTime.isAfter(now)) {
				Period remainingTimePeriod = new Period(now, missionEndTime, PeriodType.seconds());
				remainingTime = remainingTimePeriod.getSeconds();
			} else {
				remainingTime = 0;
			}
		}
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MissionStatus getStatus() {
		return status;
	}

	public void setStatus(MissionStatus status) {
		this.status = status;
	}

	public MissionRequirement[] getRequirements() {
		return requirements;
	}

	public void setRequirements(MissionRequirement[] requirements) {
		this.requirements = requirements;
	}

	public int getRequiredTime() {
		return requiredTime;
	}

	public void setRequiredTime(int requiredTime) {
		this.requiredTime = requiredTime;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}
}
