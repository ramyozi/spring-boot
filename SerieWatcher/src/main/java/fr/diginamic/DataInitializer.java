package fr.diginamic;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.diginamic.dto.ShowDTO;
import fr.diginamic.services.ShowService;



@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
    private ShowService showService;

    @Override
    public void run(String... args) throws Exception {
        createShows();
    }

    private void createShows() {
        createShow("Naruto", new String[]{"Action", "Adventure"});
        createShow("Attack on Titan", new String[]{"Action", "Drama", "Fantasy"});
        createShow("My Hero Academia", new String[]{"Action", "Comedy", "School"});
        createShow("One Piece", new String[]{"Action", "Adventure", "Comedy"});
        createShow("Tokyo Ghoul", new String[]{"Horror", "Mystery", "Supernatural"});
        createShow("Sword Art Online", new String[]{"Action", "Adventure", "Fantasy"});
    }

    private void createShow(String title, String[] genres) {
        ShowDTO showDTO = new ShowDTO();
        showDTO.setTitle(title);
        showDTO.setGenreNames(new HashSet<>(Arrays.asList(genres)));
        showService.createShow(showDTO);
    }
}
