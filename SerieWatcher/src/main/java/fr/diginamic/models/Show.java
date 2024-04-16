package fr.diginamic.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String title;

    @ManyToMany(mappedBy = "shows")
    private Set<Genre> genres;

    // Constructors
    public Show() {}

    public Show(String title) {
        this.title = title;
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

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    // Add helper method to add Genre to Show
    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getShows().add(this);
    }

    // Remove helper method to remove Genre from Show
    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.getShows().remove(this);
    }
}
