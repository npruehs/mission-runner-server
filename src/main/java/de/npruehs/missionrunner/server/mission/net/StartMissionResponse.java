package de.npruehs.missionrunner.server.mission.net;

import de.npruehs.missionrunner.server.character.CharacterStatus;
import de.npruehs.missionrunner.server.mission.MissionStatus;

public class StartMissionResponse {
	private String accountId;
    private MissionUpdate mission;
    private CharacterUpdate[] characters;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public MissionUpdate getMission() {
        return mission;
    }

    public void setMission(MissionUpdate mission) {
        this.mission = mission;
    }

    public CharacterUpdate[] getCharacters() {
        return characters;
    }

    public void setCharacters(CharacterUpdate[] characters) {
        this.characters = characters;
    }

    public static class MissionUpdate {
        private long id;
        private MissionStatus status;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public MissionStatus getStatus() {
            return status;
        }

        public void setStatus(MissionStatus status) {
            this.status = status;
        }
    }

    public static class CharacterUpdate {
        private long id;
        private CharacterStatus status;
        private long missionId;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public CharacterStatus getStatus() {
            return status;
        }

        public void setStatus(CharacterStatus status) {
            this.status = status;
        }

        public long getMissionId() {
            return missionId;
        }

        public void setMissionId(long missionId) {
            this.missionId = missionId;
        }
    }
}
