package de.npruehs.missionrunner.server.mission.net;

import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.mission.Mission;
import lombok.Getter;

public class FinishMissionRequest {
	@Getter
	private String accountId;
	
	@Getter
    private long missionId;

    public FinishMissionRequest() {
    }
    
    public FinishMissionRequest(Account account, Mission mission) {
    	accountId = account.getId();
    	missionId = mission.getId();
    }
}
