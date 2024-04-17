package fr.diginamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.diginamic.models.Genre;
import fr.diginamic.models.Show;
import fr.diginamic.repositories.GenreRepository;
import fr.diginamic.repositories.ShowRepository;
import fr.diginamic.utils.DataParser;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private DataParser dataParser;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
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