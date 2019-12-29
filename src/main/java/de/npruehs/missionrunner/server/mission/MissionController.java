package de.npruehs.missionrunner.server.mission;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MissionController {
	@RequestMapping("/missions/get")
	public Mission[] get(@RequestParam(value = "accountId") String accountId) {
		String[] requirements = new String[2];
		requirements[0] = "A";
		requirements[1] = "B";
		
		Mission[] missions = new Mission[2];
		missions[0] = new Mission(0, accountId, "TestMissionA", MissionStatus.OPEN, requirements, 10, 100);
		missions[1] = new Mission(1, accountId, "TestMissionB", MissionStatus.OPEN, requirements, 10, 100);
		
		return missions;
	}
}
