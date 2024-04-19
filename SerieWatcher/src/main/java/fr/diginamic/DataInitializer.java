package fr.diginamic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.models.Genre;
import fr.diginamic.models.Show;
import fr.diginamic.models.UserAccount;
import fr.diginamic.repositories.GenreRepository;
import fr.diginamic.repositories.ShowRepository;
import fr.diginamic.repositories.UserAccountRepository;
import fr.diginamic.utils.DataParser;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private DataParser dataParser;
    
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
    	UserAccount a = new UserAccount(
				"Luffy",
				"Luffy",
				"ADMIN"
			);
	
	UserAccount b = new UserAccount(
			"Zorro",
			"Zorro",
			"USER"
		);
	
	UserAccount c = new UserAccount(
			"test",
			"test",
			"USER"
		);
	
	userAccountRepository.save(a);
	userAccountRepository.save(b);
	userAccountRepository.save(c);
    	
        List<Show> shows = dataParser.parseShows("classpath:shows.json");
        for (Show show : shows) {
            Set<Genre> managedGenres = new HashSet<>();
            for (Genre genre : show.getGenres()) {
                Genre managedGenre = genreRepository.findByName(genre.getName());
                if (managedGenre == null) {
                    managedGenre = genreRepository.save(genre); 
                }
                managedGenres.add(managedGenre);
            }
            show.setGenres(managedGenres);
            showRepository.save(show);
        }
    }
}