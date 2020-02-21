package de.npruehs.missionrunner.server.analytics;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class AnalyticsEventSender {
	public void sendEvent(String eventId) {
		AnalyticsEvent analyticsEvent = new AnalyticsEvent();
		analyticsEvent.setEventId(eventId);
		
		WebClient.create("http://localhost:8090")
		  .put()
		  .uri("/analytics/put")
		  .body(BodyInserters.fromValue(analyticsEvent))
		  .retrieve()
          .bodyToMono(Void.class)
          .subscribe();
	}
}
