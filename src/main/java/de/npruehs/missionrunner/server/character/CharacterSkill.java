package de.npruehs.missionrunner.server.character;

public class CharacterSkill {
	private final String skill;

    private final int count;
    
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
}
