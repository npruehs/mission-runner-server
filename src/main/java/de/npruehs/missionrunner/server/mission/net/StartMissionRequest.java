package de.npruehs.missionrunner.server.mission.net;

import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.mission.Mission;

public class StartMissionRequest {
	private String accountId;
    private long missionId;
    private Long[] characterIds;

    public StartMissionRequest() {
    }
    
    public StartMissionRequest(Account account, Mission mission, Character character) {
    	accountId = account.getId();
    	missionId = mission.getId();
    	characterIds = new Long[] { character.getId() };
    }
    
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getMissionId() {
        return missionId;
    }

    public void setMissionId(long missionId) {
        this.missionId = missionId;
    }

    public Long[] getCharacterIds() {
        return characterIds;
    }

    public void setCharacterIds(Long[] characterIds) {
        this.characterIds = characterIds;
    }
}
