package fr.diginamic.models;

import jakarta.persistence.*;

@Entity
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 100, nullable = false)
	private String name;
	@Column(nullable = false)
	private int population;
	@ManyToOne()
	@JoinColumn(name = "departement_id")
	private Departement departement;

	public City() {
	}

	public City(String name, int population, Departement departement) {
		this.name = name;
		this.population = population;
		this.departement = departement;
	}

	// Getters and Setters
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}
}
