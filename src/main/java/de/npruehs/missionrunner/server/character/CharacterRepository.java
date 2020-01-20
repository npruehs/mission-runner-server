package de.npruehs.missionrunner.server.character;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.npruehs.missionrunner.server.account.Account;

public interface CharacterRepository extends CrudRepository<Character, Integer> {
	List<Character> findByAccount(Account account);
}
