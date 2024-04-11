package fr.diginamic.dto;

public class CityDto {
    private String cityCode;
    private int population;
    private String departementCode;
    private String departementName;
    
	/** Getter
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}
	/** Setter
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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
	/** Getter
	 * @return the departementCode
	 */
	public String getDepartementCode() {
		return departementCode;
	}
	/** Setter
	 * @param departementCode the departementCode to set
	 */
	public void setDepartementCode(String departementCode) {
		this.departementCode = departementCode;
	}
	/** Getter
	 * @return the departementName
	 */
	public String getDepartementName() {
		return departementName;
	}
	/** Setter
	 * @param departementName the departementName to set
	 */
	public void setDepartementName(String departementName) {
		this.departementName = departementName;
	}
    
    
    
}

