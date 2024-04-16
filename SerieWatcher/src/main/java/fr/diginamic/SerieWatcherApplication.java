package fr.diginamic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SerieWatcherApplication {

	@Value("${spring.datasource.url}")
	private static String datasourceUrl;

	public static void main(String[] args) {
		SpringApplication.run(SerieWatcherApplication.class, args);
		System.out.println("Database URL: " + datasourceUrl);
	}
}
