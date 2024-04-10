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
	private CityService villeService;

	/**
	 * Récupère la liste de toutes les villes.
	 * 
	 * @return la liste des villes
	 */
	@GetMapping
	public ResponseEntity<List<City>> getVilles() {
		List<City> villes = villeService.extractVilles();
		return ResponseEntity.ok(villes);
	}

	/**
	 * Récupère une ville par son identifiant.
	 * 
	 * @param id l'identifiant de la ville
	 * @return une réponse contenant la ville ou un statut NOT FOUND
	 */
	@GetMapping("/{id}")
	public ResponseEntity<City> getVilleById(@PathVariable int id) {
		City ville = villeService.extractVille(id);
		return ville != null ? ResponseEntity.ok(ville)
				: ResponseEntity.notFound().build();
	}

	/**
	 * Récupère une ville par son nom.
	 * 
	 * @param nom le nom de la ville
	 * @return une réponse contenant la ville ou un statut NOT FOUND
	 */
	@GetMapping("/name/{nom}")
	public ResponseEntity<City> getVilleByName(@PathVariable String nom) {
		City ville = villeService.extractVille(nom);
		return ville != null ? ResponseEntity.ok(ville)
				: ResponseEntity.notFound().build();
	}

	/**
	 * Ajoute une nouvelle ville à la base de données.
	 * 
	 * @param nouvelleVille la ville à ajouter
	 * @return la liste mise à jour des villes
	 */
	@PostMapping
	public ResponseEntity<City> ajouterVille(@RequestBody City nouvelleVille) {
	    City savedVille = villeService.insertVille(nouvelleVille);
	    return ResponseEntity.ok(savedVille);
	}

	/**
	 * Modifie les informations d'une ville existante.
	 * 
	 * @param id            l'identifiant de la ville à modifier
	 * @param villeModifiee les nouvelles données de la ville
	 * @return la liste mise à jour des villes
	 */
	@PutMapping("/{id}")
	public ResponseEntity<List<City>> modifierVille(@PathVariable int id,
			@RequestBody City villeModifiee) {
		List<City> villes = villeService.modifierVille(id, villeModifiee);
		return ResponseEntity.ok(villes);
	}

	/**
	 * Supprime une ville de la base de données en fonction de son identifiant.
	 * 
	 * @param id l'identifiant de la ville à supprimer
	 * @return la liste mise à jour des villes
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<List<City>> supprimerVille(
			@PathVariable int id) {
		List<City> villes = villeService.supprimerVille(id);
		return ResponseEntity.ok(villes);
	}
}
