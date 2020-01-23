package de.npruehs.missionrunner.server.character;

import java.io.Serializable;

public class CharacterSkill implements Serializable {
	private static final long serialVersionUID = -9065268795643338559L;

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
