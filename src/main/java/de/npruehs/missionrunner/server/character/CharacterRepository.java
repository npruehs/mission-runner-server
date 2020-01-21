package de.npruehs.missionrunner.server.character;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.npruehs.missionrunner.server.account.Account;
import de.npruehs.missionrunner.server.mission.Mission;

public interface CharacterRepository extends CrudRepository<Character, Long> {
	List<Character> findByAccount(Account account);
	List<Character> findByMission(Mission mission);
}
