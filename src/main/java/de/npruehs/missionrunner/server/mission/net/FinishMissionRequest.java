package de.npruehs.missionrunner.server.mission.net;

import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.mission.Mission;

public class FinishMissionRequest {
	private String accountId;
    private long missionId;

    public FinishMissionRequest() {
    }
    
    public FinishMissionRequest(Account account, Mission mission) {
    	accountId = account.getId();
    	missionId = mission.getId();
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
}
