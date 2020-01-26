package de.npruehs.missionrunner.server.mission;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import de.npruehs.missionrunner.server.account.Account;

@Entity
public class Mission {
	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
    private Account account;

    private String name;

    private MissionStatus status;

    private MissionRequirement[] requirements;

    private int requiredTime;

    private Timestamp startTime;
    
    private int reward;
    
    public Mission() {
    }
    
    public Mission(Account account, String name, MissionRequirement[] requirements, int requiredTime, int reward) {
    	this.account = account;
    	this.name = name;
    	this.requirements = requirements;
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
		return requirements;
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
		this.requirements = requirements;
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
