package de.npruehs.missionrunner.server.character;

public class CharacterData {
    private long id;
    private String name;
    private CharacterStatus status;
    private long missionId;
    private CharacterSkill[] skills;
    
    public CharacterData() {
    }
    
    public CharacterData(Character character) {
    	this.id = character.getId();
    	this.name = character.getName();
    	this.status = character.getStatus();
    	this.missionId = character.getMission() != null ? character.getMission().getId() : 0L;
    	this.skills = character.getSkills();
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

	public CharacterStatus getStatus() {
		return status;
	}

	public void setStatus(CharacterStatus status) {
		this.status = status;
	}

	public long getMissionId() {
		return missionId;
	}

	public void setMissionId(long missionId) {
		this.missionId = missionId;
	}

	public CharacterSkill[] getSkills() {
		return skills;
	}

	public void setSkills(CharacterSkill[] skills) {
		this.skills = skills;
	}
}
