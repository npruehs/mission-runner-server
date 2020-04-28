package de.npruehs.missionrunner.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Profile("debug")
public class DebugProfileComponent {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		System.out.println("DebugProfileComponent");
		
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/account/get").allowedOrigins("http://localhost:4200");
				registry.addMapping("/missions/get").allowedOrigins("http://localhost:4200");
				registry.addMapping("/missions/start").allowedOrigins("http://localhost:4200");
				registry.addMapping("/missions/finish").allowedOrigins("http://localhost:4200");
				registry.addMapping("/characters/get").allowedOrigins("http://localhost:4200");
				registry.addMapping("/localization/get").allowedOrigins("http://localhost:4200");
			}
		};
	}
}
