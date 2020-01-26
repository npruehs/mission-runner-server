package de.npruehs.missionrunner.server.mission.net;

import de.npruehs.missionrunner.server.character.CharacterSkill;
import de.npruehs.missionrunner.server.character.CharacterStatus;
import de.npruehs.missionrunner.server.mission.Mission;
import de.npruehs.missionrunner.server.mission.MissionData;

public class FinishMissionResponse {
	private MissionUpdate missions;
    private AccountUpdate account;
    private CharacterUpdate[] characters;
    
    public MissionUpdate getMissions() {
        return missions;
    }

    public void setMissions(MissionUpdate missions) {
        this.missions = missions;
    }

    public AccountUpdate getAccount() {
        return account;
    }

    public void setAccount(AccountUpdate account) {
        this.account = account;
    }

    public CharacterUpdate[] getCharacters() {
		return characters;
	}

	public void setCharacters(CharacterUpdate[] characters) {
		this.characters = characters;
	}

	public static class MissionUpdate {
        private long[] removedMissions;
        private MissionData[] addedMissions;

        public long[] getRemovedMissions() {
            return removedMissions;
        }

        public void setRemovedMissions(long[] removedMissions) {
            this.removedMissions = removedMissions;
        }

        public MissionData[] getAddedMissions() {
            return addedMissions;
        }

        public void setAddedMissions(MissionData[] addedMissions) {
            this.addedMissions = addedMissions;
        }
    }

    public static class AccountUpdate {
        private int score;
        private int level;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
    
    public static class CharacterUpdate {
        private long id;
        private String name;
        private CharacterStatus status;
        private long missionId;
        private CharacterSkill[] skills;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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

		public CharacterSkill[] getSkills() {
			return skills;
		}

		public void setSkills(CharacterSkill[] skills) {
			this.skills = skills;
		}
    }
}
