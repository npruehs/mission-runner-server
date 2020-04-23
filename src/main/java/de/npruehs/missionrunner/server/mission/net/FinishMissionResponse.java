package de.npruehs.missionrunner.server.mission.net;

import de.npruehs.missionrunner.server.character.CharacterSkill;
import de.npruehs.missionrunner.server.character.CharacterStatus;
import de.npruehs.missionrunner.server.mission.MissionData;
import lombok.Getter;
import lombok.Setter;

public class FinishMissionResponse {
	@Getter
	@Setter
	private MissionUpdate missions;
	
	@Getter
	@Setter
    private AccountUpdate account;
	
	@Getter
	@Setter
    private CharacterUpdate[] characters;
    
	public static class MissionUpdate {
		@Getter
		@Setter
        private long[] removedMissions;
		
		@Getter
		@Setter
        private MissionData[] addedMissions;
    }

    public static class AccountUpdate {
		@Getter
		@Setter
        private int score;
    	
		@Getter
		@Setter
        private int level;
    }
    
    public static class CharacterUpdate {
		@Getter
		@Setter
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
    }
}
