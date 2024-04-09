package fr.diginamic.models;

public class City {
	String name;
	int population;
	
	
	/** Constructeur
	 * @param name
	 * @param population
	 */
	public City(String name, int population) {
		super();
		this.name = name;
		this.population = population;
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


	@Override
	public String toString() {
		return "city [name=" + name + ", population=" + population + "]";
	}
}
