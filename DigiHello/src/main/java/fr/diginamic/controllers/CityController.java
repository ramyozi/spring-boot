package fr.diginamic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.models.City;
import fr.diginamic.services.CityService;


@RestController
@RequestMapping("/cities")
public class CityController {

	@Autowired
	private CityService cityService;

	/**
	 * Récupère la liste de toutes les villes.
	 * 
	 * @return la liste des villes
	 */
	@GetMapping
	public ResponseEntity<List<City>> getCities() {
		List<City> cities = cityService.extractCities();
		return ResponseEntity.ok(cities);
	}

	/**
	 * Récupère une ville par son identifiant.
	 * 
	 * @param id l'identifiant de la ville
	 * @return une réponse contenant la ville ou un statut NOT FOUND
	 */
	@GetMapping("/{id}")
	public ResponseEntity<City> getCityById(@PathVariable int id) {
		City city = cityService.extractCity(id);
		return city != null ? ResponseEntity.ok(city)
				: ResponseEntity.notFound().build();
	}

	/**
	 * Récupère une ville par son nom.
	 * 
	 * @param nom le nom de la ville
	 * @return une réponse contenant la ville ou un statut NOT FOUND
	 */
	@GetMapping("/name/{nom}")
	public ResponseEntity<City> getCityByName(@PathVariable String nom) {
		City city = cityService.extractCity(nom);
		return city != null ? ResponseEntity.ok(city)
				: ResponseEntity.notFound().build();
	}

	/**
	 * Ajoute une nouvelle ville à la base de données.
	 * 
	 * @param nouvelleCity la ville à créer
	 * @return la liste mise à jour des villes
	 */
	@PostMapping
	public ResponseEntity<City> createCity(@RequestBody City nouvelleCity) {
	    City savedCity = cityService.createCity(nouvelleCity);
	    return ResponseEntity.ok(savedCity);
	}

	/**
	 * Modifie les informations d'une city existante.
	 * 
	 * @param id            l'identifiant de la ville à modifier
	 * @param cityModifiee les nouvelles données de la ville
	 * @return la liste mise à jour des villes
	 */
	@PutMapping("/{id}")
	public ResponseEntity<List<City>> updateCity(@PathVariable int id,
			@RequestBody City cityModifiee) {
		List<City> cities = cityService.updateCity(id, cityModifiee);
		return ResponseEntity.ok(cities);
	}

	/**
	 * Supprime une ville de la base de données en fonction de son identifiant.
	 * 
	 * @param id l'identifiant de la ville à supprimer
	 * @return la liste mise à jour des villes
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<List<City>> deleteCity(
			@PathVariable int id) {
		List<City> cities = cityService.deleteCity(id);
		return ResponseEntity.ok(cities);
	}
	
	/**
     * Endpoint pour initialiser la base de données avec des départements et des villes prédéfinis.
     *
     * @return ResponseEntity avec un message de succès.
     */
    @PostMapping("/init")
    public ResponseEntity<String> initializeData() {
        cityService.initData();
        return ResponseEntity.ok("Data initialized successfully");
    }
}
