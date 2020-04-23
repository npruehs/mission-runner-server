package de.npruehs.missionrunner.server.character;

import java.util.ArrayList;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.common.collect.Lists;

import de.npruehs.missionrunner.server.JpaConverterJson;
import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.mission.Mission;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Character {
	@Id
	@GeneratedValue
	@Getter
	@Setter
    private long id;

    @ManyToOne
    private Account account;
    
    @Getter
	@Setter
    private String name;

    @Enumerated(EnumType.STRING)
    @Getter
	@Setter
    private CharacterStatus status;

    @ManyToOne
    @Getter
	@Setter
    private Mission mission;

    @Convert(converter = JpaConverterJson.class)
    private ArrayList<CharacterSkill> skills;

    public Character() {
    }
    
	public Character(Account account, String name, CharacterSkill[] skills) {
		this.account = account;
		this.name = name;
		this.skills = Lists.newArrayList(skills);
		
		this.status = CharacterStatus.IDLE;
	}

	public CharacterSkill[] getSkills() {
		return skills.toArray(new CharacterSkill[skills.size()]);
	}

	public void setSkills(CharacterSkill[] skills) {
		this.skills = Lists.newArrayList(skills);
	}
}
