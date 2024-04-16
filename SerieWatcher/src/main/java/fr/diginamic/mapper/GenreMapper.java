package fr.diginamic.mapper;

import java.util.List;
import java.util.stream.Collectors;

import fr.diginamic.dto.GenreDTO;
import fr.diginamic.models.Genre;

public class GenreMapper {

    public static GenreDTO toDTO(Genre genre) {
        if (genre == null) {
            return null;
        }
        return new GenreDTO(genre.getId(), genre.getName());
    }

    public static Genre toEntity(GenreDTO genreDTO) {
        if (genreDTO == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setName(genreDTO.getName());
        return genre;
    }

    public static List<GenreDTO> toDTOList(List<Genre> genres) {
        if (genres == null) {
            return null;
        }
        return genres.stream()
                     .map(GenreMapper::toDTO)
                     .collect(Collectors.toList());
    }

    public static List<Genre> toEntityList(List<GenreDTO> genreDTOs) {
        if (genreDTOs == null) {
            return null;
        }
        return genreDTOs.stream()
                        .map(GenreMapper::toEntity)
                        .collect(Collectors.toList());
    }
}
