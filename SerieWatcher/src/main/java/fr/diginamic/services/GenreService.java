package fr.diginamic.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.dto.GenreDTO;
import fr.diginamic.mapper.GenreMapper;
import fr.diginamic.models.Genre;
import fr.diginamic.repositories.GenreRepository;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Transactional
    public GenreDTO createGenre(GenreDTO genreDTO) {
        Genre genre = GenreMapper.toEntity(genreDTO);
        genre = genreRepository.save(genre);
        return GenreMapper.toDTO(genre);
    }

    @Transactional(readOnly = true)
    public GenreDTO getGenreById(int id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new RuntimeException("Genre not found: " + id));
        return GenreMapper.toDTO(genre);
    }
    
    @Transactional(readOnly = true)
    public GenreDTO getGenreByName(String name) {
        Genre genre = genreRepository.findByName(name);
        return GenreMapper.toDTO(genre);
    }
    

    @Transactional(readOnly = true)
    public List<GenreDTO> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream().map(GenreMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public GenreDTO updateGenre(GenreDTO genreDTO) {
        Genre existingGenre = genreRepository.findById(genreDTO.getId()).orElseThrow(() -> new RuntimeException("Genre not found: " + genreDTO.getId()));
        existingGenre.setName(genreDTO.getName());
        genreRepository.save(existingGenre);
        return GenreMapper.toDTO(existingGenre);
    }

    @Transactional
    public void deleteGenre(int id) {
        genreRepository.deleteById(id);
    }
}
