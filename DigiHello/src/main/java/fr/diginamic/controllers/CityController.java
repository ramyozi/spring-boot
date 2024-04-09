package fr.diginamic.controllers;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {
	
	private final List<City> villes = new ArrayList<>();
	
	/**
     * Constructeur qui initialise la liste des villes avec des données de test.
     */
	public CityController() {
        villes.add(new City(1,"Paris", 1111111));
        villes.add(new City(2, "Lyon", 2222222));
    }

	/**
     * Récupère la liste de toutes les villes.
     * 
     * @return la liste des villes
     */
	 @GetMapping
	    public List<City> getVilles() {
	        return villes;
	    }

	 /**
     * Récupère une ville par son identifiant.
     * 
     * @param id l'identifiant de la ville
     * @return une réponse contenant la ville ou un statut NOT FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<City> getVilleById(@PathVariable int id) {
        return villes.stream()
                .filter(ville -> ville.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Ajoute une nouvelle ville à la liste.
     * 
     * @param nouvelleVille la ville à ajouter
     * @return une réponse indiquant si l'opération a réussi ou échoué
     */
    @PostMapping
    public ResponseEntity<String> ajouterVille(@RequestBody City nouvelleVille) {
        boolean exists = villes.stream().anyMatch(v -> v.getId() == nouvelleVille.getId());
        if (exists) {
            return ResponseEntity.badRequest().body("Une ville avec cet ID existe déjà");
        } else {
            villes.add(nouvelleVille);
            return ResponseEntity.ok("Ville ajoutée avec succès");
        }
    }
    
    /**
     * Modifie les informations d'une ville existante.
     * 
     * @param id l'identifiant de la ville à modifier
     * @param villeModifiee les nouvelles données de la ville
     * @return une réponse indiquant si la modification a été effectuée avec succès
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierVille(@PathVariable int id, @RequestBody City villeModifiee) {
        Optional<City> villeExistante = villes.stream().filter(v -> v.getId() == id).findFirst();
        if (villeExistante.isPresent()) {
            villeExistante.get().setName(villeModifiee.getName());
            villeExistante.get().setPopulation(villeModifiee.getPopulation());
            return ResponseEntity.ok("Ville modifiée avec succès");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Supprime une ville de la liste en fonction de son identifiant.
     * 
     * @param id l'identifiant de la ville à supprimer
     * @return une réponse indiquant si la suppression a été effectuée avec succès
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerVille(@PathVariable int id) {
        boolean removed = villes.removeIf(v -> v.getId() == id);
        if (removed) {
            return ResponseEntity.ok("Ville supprimée avec succès");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


