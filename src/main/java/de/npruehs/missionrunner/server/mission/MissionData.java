package de.npruehs.missionrunner.server.mission;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import lombok.Getter;

public class MissionData {
	@Getter
	private long id;
	
	@Getter
    private String name;
	
	@Getter
    private MissionStatus status;
	
	@Getter
    private MissionRequirement[] requirements;
	
	@Getter
    private int requiredTime;
    
	@Getter
    private int remainingTime;
	
	@Getter
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
}
