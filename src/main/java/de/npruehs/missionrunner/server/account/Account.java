package de.npruehs.missionrunner.server.account;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import de.npruehs.missionrunner.server.character.Character;
import de.npruehs.missionrunner.server.mission.Mission;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Account {
	@Id
	@Getter
	private String id;
	
	@OneToMany(mappedBy="account", cascade = { CascadeType.ALL })
	@Getter
	@Setter
	private Collection<Character> characters;
	
	@OneToMany(mappedBy="account", cascade = { CascadeType.ALL })
	@Getter
	@Setter
	private Collection<Mission> missions;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private int level;
	
	@Getter
	@Setter
	private int score;

	public Account() {
	}
	
	public Account(String id, String name, int level, int score) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.score = score;
	}
}
