package fr.diginamic.dto;

import java.util.Date;
import java.util.Set;

public class ShowDTO {
	private int id;
	private String title;
	private String imageUrl;
	private String description;
	private Set<String> genreNames;
	private String status;
	private Date releaseDate;

	// Constructors
	public ShowDTO() {
	}

	public ShowDTO(int id, String title, String imageUrl,
			Set<String> genreNames, String description, String status,
			Date releaseDate) {
		this.id = id;
		this.title = title;
		this.imageUrl = imageUrl;
		this.genreNames = genreNames;
		this.description = description;
		this.status = status;
		this.releaseDate = releaseDate;
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

	/**
	 * Getter
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Setter
	 * 
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Getter
	 * 
	 * @return the releaseDate
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Setter
	 * 
	 * @param releaseDate the releaseDate to set
	 */
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
