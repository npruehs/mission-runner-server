package de.npruehs.missionrunner.server.mission.net;

import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.mission.Mission;
import lombok.Getter;

public class StartMissionRequest {
	@Getter
	private String accountId;
	
	@Getter
    private long missionId;
	
	@Getter
    private Long[] characterIds;

    public StartMissionRequest() {
    }
    
    public StartMissionRequest(Account account, Mission mission, Character character) {
    	accountId = account.getId();
    	missionId = mission.getId();
    	characterIds = new Long[] { character.getId() };
    }
}
