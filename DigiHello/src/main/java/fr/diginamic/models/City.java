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
    
    
	
	/** Constructeur (pour JPA)
	 * 
	 */
	public City() {
		super();
	}

	/** Constructeur
	 * @param name
	 * @param population
	 */
	public City(String name, int population) {
        super();
        this.name = name;
        this.population = population;
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
}
