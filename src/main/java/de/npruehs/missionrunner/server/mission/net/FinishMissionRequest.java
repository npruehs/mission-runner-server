package de.npruehs.missionrunner.server.mission.net;

public class FinishMissionRequest {
	private String accountId;
    private long missionId;

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
