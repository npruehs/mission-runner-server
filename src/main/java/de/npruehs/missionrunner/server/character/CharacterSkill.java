package de.npruehs.missionrunner.server.character;

public class CharacterSkill {
	private String skill;

    private int count;
    
    public CharacterSkill() {
    }
    
    public CharacterSkill(String skill, int count) {
    	this.skill = skill;
    	this.count = count;
    }

	public String getSkill() {
		return skill;
	}

	public int getCount() {
		return count;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
