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
        List<City> cities = em.createQuery("SELECT c FROM City c", City.class).getResultList();
        return cities.stream().map(CityMapper::cityToDto).collect(Collectors.toList());
    }


	@Transactional
	public City createCity(City city) {
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
		Departement dep75 = departementService
				.saveDepartement(new Departement("Paris", "75"));
		Departement dep13 = departementService.saveDepartement(
				new Departement("Bouches-du-Rhône", "13"));
		Departement dep31 = departementService
				.saveDepartement(new Departement("Haute-Garonne", "31"));

		createCity(new City("Paris", 1111111, dep75));
		createCity(new City("Marseille", 2222222, dep13));
		createCity(new City("Toulouse", 333333, dep31));
	}
	
	@Transactional
	public List<City> findTopNCitiesByDepartment(Long depId, int n) {
	    return em.createQuery("SELECT c FROM City c WHERE c.departement.id = :depId ORDER BY c.population DESC", City.class)
	             .setParameter("depId", depId)
	             .setMaxResults(n)
	             .getResultList();
	}

	@Transactional
	public List<City> findCitiesByPopulationRangeAndDepartment(Long depId, int minPopulation, int maxPopulation) {
	    return em.createQuery("SELECT c FROM City c WHERE c.departement.id = :depId AND c.population BETWEEN :min AND :max ORDER BY c.population", City.class)
	             .setParameter("depId", depId)
	             .setParameter("min", minPopulation)
	             .setParameter("max", maxPopulation)
	             .getResultList();
	}

}
