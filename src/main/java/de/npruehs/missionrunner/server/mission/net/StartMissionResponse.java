package de.npruehs.missionrunner.server.mission.net;

import de.npruehs.missionrunner.server.character.CharacterStatus;
import de.npruehs.missionrunner.server.mission.MissionStatus;
import lombok.Getter;
import lombok.Setter;

public class StartMissionResponse {
	@Getter
	@Setter
	private String accountId;
	
	@Getter
	@Setter
    private MissionUpdate mission;
    
	@Getter
	@Setter
    private CharacterUpdate[] characters;

    public static class MissionUpdate {
    	@Getter
    	@Setter
        private long id;
        
    	@Getter
    	@Setter
        private MissionStatus status;
    }

    public static class CharacterUpdate {
    	@Getter
    	@Setter
        private long id;
        
    	@Getter
    	@Setter
        private CharacterStatus status;
        
    	@Getter
    	@Setter
        private long missionId;
    }
}
