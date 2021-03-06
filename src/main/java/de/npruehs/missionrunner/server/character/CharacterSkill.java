package de.npruehs.missionrunner.server.character;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class CharacterSkill {
	@Getter
	private String skill;

	@Getter
    private int count;
    
    public CharacterSkill() {
    }
    
    public CharacterSkill(String skill, int count) {
    	this.skill = skill;
    	this.count = count;
    }
}
