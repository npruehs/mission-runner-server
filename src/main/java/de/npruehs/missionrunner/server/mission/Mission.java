package de.npruehs.missionrunner.server.mission;

public class Mission {
	private final int id;

    private final String accountId;

    private final String name;

    private final MissionStatus status;

    private final String[] requirements;

    private final int requiredTime;

    private final int reward;
    
    public Mission(int id, String accountId, String name, MissionStatus status, String[] requirements, int requiredTime, int reward) {
    	this.id = id;
    	this.accountId = accountId;
    	this.name = name;
    	this.status = status;
    	this.requirements = requirements;
    	this.requiredTime = requiredTime;
    	this.reward = reward;
    }

	public int getId() {
		return id;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getName() {
		return name;
	}

	public MissionStatus getStatus() {
		return status;
	}

	public String[] getRequirements() {
		return requirements;
	}

	public int getRequiredTime() {
		return requiredTime;
	}

	public int getReward() {
		return reward;
	}
}
