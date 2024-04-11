package fr.diginamic.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Departement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 100, nullable = false)
	private String name;
	@Column(length = 10, nullable = false, unique = true)
	private String code;
	
	@JsonIgnore
	@OneToMany(mappedBy = "departement", orphanRemoval = true)
	private Set<City> villes = new HashSet<>();

	public Departement() {
	}

	public Departement(String name, String code) {
		this.name = name;
		this.code = code;
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<City> getVilles() {
		return villes;
	}

	public void setVilles(Set<City> villes) {
		this.villes = villes;
	}

	public void addVille(City ville) {
		villes.add(ville);
		ville.setDepartement(this);
	}

	public void removeVille(City ville) {
		villes.remove(ville);
		ville.setDepartement(null);
	}
}
