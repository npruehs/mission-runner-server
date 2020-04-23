package de.npruehs.missionrunner.server.mission;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.common.collect.Lists;

import de.npruehs.missionrunner.server.account.Account;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Mission {
	@Id
	@GeneratedValue
	@Getter
	@Setter
	private long id;

	@ManyToOne
    private Account account;

	@Getter
    private String name;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private MissionStatus status;

    @Convert(converter = MissionRequirementJpaConverter.class)
    private ArrayList<MissionRequirement> requirements;

    @Getter
    private int requiredTime;

    @Getter
    @Setter
    private Timestamp startTime;
    
    @Getter
    private int reward;
    
    public Mission() {
    }
    
    public Mission(Account account, String name, MissionRequirement[] requirements, int requiredTime, int reward) {
    	this.account = account;
    	this.name = name;
    	this.requirements = Lists.newArrayList(requirements);
    	this.requiredTime = requiredTime;
    	this.reward = reward;
    	
    	this.status = MissionStatus.OPEN;
    }

	public MissionRequirement[] getRequirements() {
		return requirements.toArray(new MissionRequirement[requirements.size()]);
	}

	public void setRequirements(MissionRequirement[] requirements) {
		this.requirements = Lists.newArrayList(requirements);
	}
}
