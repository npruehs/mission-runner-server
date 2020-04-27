package de.npruehs.missionrunner.server.analytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsEventSender {
	private AnalyticsService analyticsService;
	
	private Logger logger = LoggerFactory.getLogger(AnalyticsEventSender.class);
	
	public AnalyticsEventSender(AnalyticsService analyticsService) {
		this.analyticsService = analyticsService;
	}
	
	public void sendEvent(String eventId) {
		AnalyticsEvent analyticsEvent = new AnalyticsEvent();
		analyticsEvent.setEventId(eventId);

		try {
			analyticsService.putEvent(analyticsEvent);
		} catch (Exception e) {
			// Unable to reach analytics isn't great - but it's definitely not worth crashing.
			logger.error(e.toString());
		}
		
		// The above approach uses OpenFeign and thus is running synchronously.
		// That might not be what we want for analytics.
		// Find a working asynchronous approach below.
		//      WebClient.create("http://localhost:8090")
		//        .put()
		//        .uri("/analytics/put")
		//        .body(BodyInserters.fromValue(analyticsEvent))
		//        .retrieve()
		//        .bodyToMono(Void.class)
		//        .subscribe();
	}
}
