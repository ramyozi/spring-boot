package fr.diginamic.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Departement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	private String nom;

	@Column(length = 10, nullable = false, unique = true)
	private String code;

    @OneToMany(mappedBy = "departement", orphanRemoval = true)
    private Set<City> villes = new HashSet<>();

	public Departement() {
	}

	public Departement(String nom, String code) {
		this.nom = nom;
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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