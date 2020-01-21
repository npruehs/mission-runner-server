package de.npruehs.missionrunner.server.mission;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.npruehs.missionrunner.server.account.Account;

public interface MissionRepository extends CrudRepository<Mission, Long> {
	List<Mission> findByAccount(Account account);
}
