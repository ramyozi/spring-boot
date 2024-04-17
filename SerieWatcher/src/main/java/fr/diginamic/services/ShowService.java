package fr.diginamic.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.diginamic.dto.ShowDTO;
import fr.diginamic.enums.StatusEnum;
import fr.diginamic.mapper.ShowMapper;
import fr.diginamic.models.Genre;
import fr.diginamic.models.Show;
import fr.diginamic.repositories.GenreRepository;
import fr.diginamic.repositories.ShowRepository;

@Service
public class ShowService {

	@Autowired
	private ShowRepository showRepository;

	@Autowired
	private GenreRepository genreRepository;

	@Transactional
	public ShowDTO createShow(ShowDTO showDTO) {
		Show show = ShowMapper.toEntity(showDTO);
		Set<Genre> genres = new HashSet<>();

		if (showDTO.getGenreNames() != null) {
			for (String genreName : showDTO.getGenreNames()) {
				Genre genre = genreRepository.findByName(genreName);
				if (genre == null) {
					genre = new Genre();
					genre.setName(genreName);
					genre = genreRepository.save(genre);
				}
				genres.add(genre);
			}
		}

		show.setGenres(genres);
		show.setDescription(showDTO.getDescription());
		try {

			show.setStatus(
					StatusEnum.valueOf(showDTO.getStatus().toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"Invalid status value: " + showDTO.getStatus());
		}
		show.setReleaseDate(showDTO.getReleaseDate());
		Show savedShow = showRepository.save(show);
		return ShowMapper.toDTO(savedShow);
	}

	@Transactional(readOnly = true)
	public ShowDTO getShowById(int id) {
		Show show = showRepository.findById(id).orElseThrow(
				() -> new RuntimeException("Show not found: " + id));
		return ShowMapper.toDTO(show);
	}

	@Transactional(readOnly = true)
	public List<ShowDTO> getAllShows() {
		List<Show> shows = showRepository.findAll();
		return shows.stream().map(ShowMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	public ShowDTO updateShow(ShowDTO showDTO) {
		Show existingShow = showRepository.findById(showDTO.getId())
				.orElseThrow(() -> new RuntimeException(
						"Show not found: " + showDTO.getId()));
		existingShow.setTitle(showDTO.getTitle());
		existingShow.setDescription(showDTO.getDescription());
		try {
			existingShow.setStatus(
					StatusEnum.valueOf(showDTO.getStatus().toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(
					"Invalid status value: " + showDTO.getStatus());
		}
		existingShow.setReleaseDate(showDTO.getReleaseDate());
		Set<Genre> updatedGenres = showDTO.getGenreNames().stream()
				.map(name -> Optional
						.ofNullable(genreRepository.findByName(name))
						.orElseThrow(() -> new RuntimeException(
								"Genre not found: " + name)))
				.collect(Collectors.toSet());
		existingShow.setGenres(updatedGenres);
		showRepository.save(existingShow);
		return ShowMapper.toDTO(existingShow);
	}

	@Transactional
	public void deleteShow(int id) {
		showRepository.deleteById(id);
	}
}
