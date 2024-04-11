package fr.diginamic.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


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
    
	
	/** Constructeur (pour JPA)
	 * 
	 */
	public City() {
		super();
	}

	/** Constructeur
	 * @param name
	 * @param population
	 * @param departement
	 */
	public City(String name, int population, Departement departement) {
	    this.name = name;
	    this.population = population;
	    this.departement = departement;
	}
	
	@Override
	public String toString() {
		return "city [name=" + name + ", population=" + population + "]";
	}

	/** Getter
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/** Setter
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}

	/** Setter
	 * @param population the population to set
	 */
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
