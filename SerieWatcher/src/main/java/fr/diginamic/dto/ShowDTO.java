package fr.diginamic.dto;

import java.util.Set;

public class ShowDTO {
    private int id;
    private String title;
    private Set<String> genreNames;

    // Constructors
    public ShowDTO() {}

    public ShowDTO(int id, String title, Set<String> genreNames) {
        this.id = id;
        this.title = title;
        this.genreNames = genreNames;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getGenreNames() {
        return genreNames;
    }

    public void setGenreNames(Set<String> genreNames) {
        this.genreNames = genreNames;
    }
}
