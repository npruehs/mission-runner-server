package de.npruehs.missionrunner.server.character;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.mission.Mission;

@Entity
public class Character {
	@Id
	@GeneratedValue
    private long id;

    @ManyToOne
	@JsonIgnore
    private Account account;
    
    private String name;

    private CharacterStatus status;

    @ManyToOne
    private Mission mission;

    private CharacterSkill[] skills;

    public Character() {
    }
    
	public Character(Account account, String name, CharacterSkill[] skills) {
		this.account = account;
		this.name = name;
		this.skills = skills;
		
		this.status = CharacterStatus.IDLE;
	}

	public long getId() {
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

	public Mission getMission() {
		return mission;
	}

	public CharacterSkill[] getSkills() {
		return skills;
	}

	public void setId(long id) {
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

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public void setSkills(CharacterSkill[] skills) {
		this.skills = skills;
	}
}
