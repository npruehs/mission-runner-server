package de.npruehs.missionrunner.server.character;

public class Character {
    private final int id;

    private final String accountId;

    private final String name;

    private final CharacterStatus status;

    private final int missionId;

    private final CharacterSkill[] skills;

	public Character(int id, String accountId, String name, CharacterStatus status, int missionId,
			CharacterSkill[] skills) {
		this.id = id;
		this.accountId = accountId;
		this.name = name;
		this.status = status;
		this.missionId = missionId;
		this.skills = skills;
	}

	public int getId() {
		return id;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getName() {
		return name;
	}

	public CharacterStatus getStatus() {
		return status;
	}

	public int getMissionId() {
		return missionId;
	}

	public CharacterSkill[] getSkills() {
		return skills;
	}
}
