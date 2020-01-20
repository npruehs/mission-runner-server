package de.npruehs.missionrunner.server.character;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import de.npruehs.missionrunner.server.account.Account;

@Entity
public class Character {
	@Id
	@GeneratedValue
    private int id;

    @ManyToOne
    private Account account;

    private String name;

    private CharacterStatus status;

    private int missionId;

    private CharacterSkill[] skills;

    public Character() {
    }
    
	public Character(Account account, String name, CharacterStatus status, int missionId,
			CharacterSkill[] skills) {
		this.account = account;
		this.name = name;
		this.status = status;
		this.missionId = missionId;
		this.skills = skills;
	}

	public int getId() {
		return id;
	}

	public Account getAccount() {
		return account;
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

	public void setId(int id) {
		this.id = id;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(CharacterStatus status) {
		this.status = status;
	}

	public void setMissionId(int missionId) {
		this.missionId = missionId;
	}

	public void setSkills(CharacterSkill[] skills) {
		this.skills = skills;
	}
}
