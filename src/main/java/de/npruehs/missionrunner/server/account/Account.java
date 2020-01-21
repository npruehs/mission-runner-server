package de.npruehs.missionrunner.server.account;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.mission.Mission;

@Entity
public class Account {
	@Id
	private String id;
	
	@OneToMany(mappedBy="account")
	private Collection<Character> characters;
	
	@OneToMany(mappedBy="account")
	private Collection<Mission> missions;
	
	private String name;
	private int level;
	private int score;

	public Account() {
	}
	
	public Account(String id, String name, int level, int score) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(Collection<Character> characters) {
		this.characters = characters;
	}
	
	public Collection<Mission> getMissions() {
		return missions;
	}

	public void setMissions(Collection<Mission> missions) {
		this.missions = missions;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
