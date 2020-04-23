package de.npruehs.missionrunner.server.character;

import lombok.Getter;
import lombok.Setter;

public class CharacterData {
	@Getter
    private long id;
	
	@Getter
	@Setter
    private String name;
	
	@Getter
	@Setter
    private CharacterStatus status;
	
	@Getter
	@Setter
    private long missionId;
	
	@Getter
	@Setter
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
}
