package de.npruehs.missionrunner.server.analytics;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "analytics", url = "http://localhost:8090")
public interface AnalyticsService {
	@RequestMapping(path = "/analytics/put", method = RequestMethod.PUT)
	public String putEvent(AnalyticsEvent analyticsEvent);
}
