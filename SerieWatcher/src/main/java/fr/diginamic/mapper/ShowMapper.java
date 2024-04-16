package fr.diginamic.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.diginamic.dto.ShowDTO;
import fr.diginamic.models.Genre;
import fr.diginamic.models.Show;

public class ShowMapper {

    public static ShowDTO toDTO(Show show) {
        if (show == null) {
            return null;
        }
        Set<String> genreNames = show.getGenres().stream()
                                     .map(Genre::getName)
                                     .collect(Collectors.toSet());
        return new ShowDTO(show.getId(), show.getTitle(), genreNames);
    }

    public static Show toEntity(ShowDTO showDTO) {
        if (showDTO == null) {
            return null;
        }
        Show show = new Show();
        show.setId(showDTO.getId());
        show.setTitle(showDTO.getTitle());
        return show;
    }

    public static List<ShowDTO> toDTOList(List<Show> shows) {
        if (shows == null) {
            return null;
        }
        return shows.stream()
                    .map(ShowMapper::toDTO)
                    .collect(Collectors.toList());
    }

    public static List<Show> toEntityList(List<ShowDTO> showDTOs) {
        if (showDTOs == null) {
            return null;
        }
        return showDTOs.stream()
                       .map(ShowMapper::toEntity)
                       .collect(Collectors.toList());
    }
}
