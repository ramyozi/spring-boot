package fr.diginamic.services;

import fr.diginamic.models.City;
import fr.diginamic.models.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    @PersistenceContext
    private EntityManager em;
    
	@Autowired
	private DepartementService departementService;


    /** Extrait toutes les villes de la base de données */
    public List<City> extractCities() {
        return em.createQuery("SELECT c FROM City c", City.class).getResultList();
    }

    /** Extrait une ville unique par son ID */
    public City extractCity(int idCity) {
        return em.find(City.class, idCity);
    }

    /** Extrait une ville unique par son nom */
    public City extractCity(String nom) {
        return em.createQuery("SELECT c FROM City c WHERE c.name = :name", City.class)
                 .setParameter("name", nom)
                 .getSingleResult();
    }

    /** Insère une nouvelle ville dans la base de données et retourne la liste de toutes les villes */
    @Transactional
    public City createCity(City city) {
        em.persist(city);
        em.flush(); 
        return city;
    }

    /** Modifie une ville existante et retourne la liste de toutes les villes */
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

    /** Supprime une ville de la base de données et retourne la liste de toutes les villes */
    @Transactional
    public List<City> deleteCity(int idCity) {
        City city = em.find(City.class, idCity);
        if (city != null) {
            em.remove(city);
        }
        return extractCities();
    }

    /**
	 * Initialise les données de départements et de villes.
	 */
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

	
}
