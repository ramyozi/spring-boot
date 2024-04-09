package fr.diginamic.models;

public class City {
    private int id;
    private String name;
    private int population;
	
	/** Constructeur
	 * @param id
	 * @param name
	 * @param population
	 */
	public City(int id, String name, int population) {
		super();
		this.id = id;
		this.name = name;
		this.population = population;
	}

	@Override
	public String toString() {
		return "city [name=" + name + ", population=" + population + "]";
	}

	/** Getter
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/** Setter
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
