package fr.diginamic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SerieWatcherApplication {

	@Value("${spring.datasource.url}")
	private static String datasourceUrl;

	public static void main(String[] args) {
        loadEnvironmentVariables();
		SpringApplication.run(SerieWatcherApplication.class, args);
		System.out.println("Database URL: " + datasourceUrl);
	}
    private static void loadEnvironmentVariables() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"));
        System.setProperty("GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET"));
    }

}
