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

import de.npruehs.missionrunner.server.JpaConverterJson;
import de.npruehs.missionrunner.server.account.Account;

@Entity
public class Mission {
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
    private Account account;

    private String name;

    @Enumerated(EnumType.STRING)
    private MissionStatus status;

    @Convert(converter = JpaConverterJson.class)
    private ArrayList<MissionRequirement> requirements;

    private int requiredTime;

    private Timestamp startTime;
    
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

	public long getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public String getName() {
		return name;
	}

	public MissionStatus getStatus() {
		return status;
	}

	public MissionRequirement[] getRequirements() {
		return requirements.toArray(new MissionRequirement[requirements.size()]);
	}

	public int getRequiredTime() {
		return requiredTime;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public int getReward() {
		return reward;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(MissionStatus status) {
		this.status = status;
	}

	public void setRequirements(MissionRequirement[] requirements) {
		this.requirements = Lists.newArrayList(requirements);
	}

	public void setRequiredTime(int requiredTime) {
		this.requiredTime = requiredTime;
	}
	
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}
}
