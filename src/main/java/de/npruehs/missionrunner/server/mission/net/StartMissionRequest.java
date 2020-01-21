package de.npruehs.missionrunner.server.mission.net;

public class StartMissionRequest {
	private String accountId;
    private long missionId;
    private Long[] characterIds;

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
