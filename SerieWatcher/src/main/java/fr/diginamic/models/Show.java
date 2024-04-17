package fr.diginamic.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import fr.diginamic.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "tv_show")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String title;
    
    @Column(length = 100, nullable = true)
    private String imageUrl;
    
    @Column(length = 512, nullable = true)
    private String description;

    @ManyToMany
    @JoinTable(
        name = "show_genre",
        joinColumns = @JoinColumn(name = "show_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @Column(nullable = false)
    private StatusEnum status;
    
    @Temporal(TemporalType.DATE) 
    private Date releaseDate;


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

    // Helper methods to manage bi-directional relationship
    public void addGenre(Genre genre) {
        genres.add(genre);
        genre.getShows().add(this);
    }

    public void removeGenre(Genre genre) {
        genres.remove(genre);
        genre.getShows().remove(this);
    }

	/** Getter
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/** Setter
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** Getter
	 * @return the status
	 */
	public StatusEnum getStatus() {
		return status;
	}

	/** Setter
	 * @param status the status to set
	 */
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

	/** Getter
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/** Setter
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
    
    
}
