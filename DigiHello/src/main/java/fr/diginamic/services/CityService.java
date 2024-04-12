package fr.diginamic.services;

import fr.diginamic.dto.CityDto;
import fr.diginamic.mapper.CityMapper;
import fr.diginamic.models.City;
import fr.diginamic.models.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

	@PersistenceContext
	private EntityManager em;

	@Autowired 
	private DepartementService departementService;

	public List<City> extractCities() {
		return em.createQuery("SELECT c FROM City c", City.class)
				.getResultList();
	}

	public City extractCity(int idCity) {
		return em.find(City.class, idCity);
	}

	public City extractCity(String nom) {
		return em
				.createQuery("SELECT c FROM City c WHERE c.name = :name",
						City.class)
				.setParameter("name", nom).getSingleResult();
	}

	@Transactional
	public CityDto getCityDtoById(int idCity) {
		City city = em.find(City.class, idCity);
		return city != null ? CityMapper.cityToDto(city) : null;
	}

	@Transactional
	public List<CityDto> getAllCityDtos() {
		List<City> cities = em
				.createQuery("SELECT c FROM City c", City.class)
				.getResultList();
		return cities.stream().map(CityMapper::cityToDto)
				.collect(Collectors.toList());
	}

	@Transactional
    public City createCity(City city) {
        if (city.getDepartement() != null && city.getDepartement().getCode() != null) {
            Departement dep = departementService.findDepartementByCode(city.getDepartement().getCode());
            if (dep == null) {
                throw new IllegalStateException("Department not found");
            }
            city.setDepartement(dep); 
        } else {
            throw new IllegalStateException("Department code must be provided");
        }
        em.persist(city);
        em.flush();
        return city;
    }

	@Transactional
	public List<City> updateCity(int idCity, City cityModifiee) {
		City city = em.find(City.class, idCity);
		if (city != null) {
			city.setName(cityModifiee.getName());
			city.setPopulation(cityModifiee.getPopulation());
			em.merge(city);
		}
		return extractCities();
	}

	@Transactional
	public List<City> deleteCity(int idCity) {
		City city = em.find(City.class, idCity);
		if (city != null) {
			em.remove(city);
		}
		return extractCities();
	}

	@Transactional
	public void initData() {
		 Departement dep75 = departementService.saveDepartement(new Departement("Paris", "75"));
	        Departement dep13 = departementService.saveDepartement(new Departement("Bouches-du-Rhône", "13"));
	        Departement dep31 = departementService.saveDepartement(new Departement("Haute-Garonne", "31"));

	        createCity(new City("Paris", 2148000, dep75));
	        createCity(new City("Boulogne-Billancourt", 120645, dep75));
	        createCity(new City("Montreuil", 109043, dep75));
	        createCity(new City("Saint-Denis", 111354, dep75));
	        createCity(new City("Nanterre", 95278, dep75));
	        createCity(new City("Créteil", 90289, dep75));
	        createCity(new City("Versailles", 85416, dep75));
	        createCity(new City("Courbevoie", 81735, dep75));
	        createCity(new City("Vitry-sur-Seine", 79315, dep75));
	        createCity(new City("Colombes", 85453, dep75));

	        createCity(new City("Marseille", 861635, dep13));
	        createCity(new City("Aix-en-Provence", 143006, dep13));
	        createCity(new City("Arles", 52949, dep13));
	        createCity(new City("Martigues", 48617, dep13));
	        createCity(new City("Aubagne", 46853, dep13));
	        createCity(new City("Istres", 42753, dep13));
	        createCity(new City("Salon-de-Provence", 45274, dep13));
	        createCity(new City("Vitrolles", 34687, dep13));
	        createCity(new City("Marignane", 33820, dep13));
	        createCity(new City("La Ciotat", 34395, dep13));

	        createCity(new City("Toulouse", 471941, dep31));
	        createCity(new City("Colomiers", 38997, dep31));
	        createCity(new City("Tournefeuille", 29175, dep31));
	        createCity(new City("Muret", 24853, dep31));
	        createCity(new City("Blagnac", 24477, dep31));
	        createCity(new City("Plaisance-du-Touch", 17685, dep31));
	        createCity(new City("Cugnaux", 16814, dep31));
	        createCity(new City("Balma", 15823, dep31));
	        createCity(new City("L'Union", 12345, dep31));
	        createCity(new City("Ramonville-Saint-Agne", 11584, dep31));
	}

	public List<City> getTopNCitiesByDepartment(String depCode, int n) {
		return em.createQuery(
				"SELECT c FROM City c WHERE c.departement.code = :depCode ORDER BY c.population DESC",
				City.class).setParameter("depCode", depCode)
				.setMaxResults(n).getResultList();
	}

	public List<City> getCitiesByPopulationRangeAndDepartment(
			String depCode, int minPopulation, int maxPopulation) {
		return em.createQuery(
				"SELECT c FROM City c WHERE c.departement.code = :depCode AND c.population BETWEEN :min AND :max ORDER BY c.population",
				City.class).setParameter("depCode", depCode)
				.setParameter("min", minPopulation)
				.setParameter("max", maxPopulation).getResultList();
	}

}
