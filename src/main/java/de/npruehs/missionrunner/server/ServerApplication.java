package de.npruehs.missionrunner.server;

import java.util.Random;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.base.Predicates;

import de.npruehs.missionrunner.server.analytics.AnalyticsEventSender;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ServerApplication {
	@Autowired
    private Environment env;
	
	@Primary
    @Bean
    public DataSource userDataSource() {
  
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
 
        return dataSource;
    }
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Bean
    public Docket api() {
		ApiInfo apiInfo = new ApiInfoBuilder()
				.contact(new Contact("Nick Pruehs", "https://github.com/npruehs", null))
				.title("MissionRunner Server API")
				.build();
		
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(apiInfo)
        		.select()
        		.apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
        		.paths(PathSelectors.any())
        		.build();
    }
	
	@Bean
	public Gameplay gameplay() {
		return new Gameplay();
	}
	
	@Bean
	public Random random() {
		return new Random();
	}
	
	@Bean
	public AnalyticsEventSender analyticsEventSender() {
		return new AnalyticsEventSender();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
