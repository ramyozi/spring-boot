package fr.diginamic.controllers;

import fr.diginamic.models.City;
import fr.diginamic.models.Departement;
import fr.diginamic.services.CityService;
import fr.diginamic.services.DepartementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/departements")
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    @Autowired
    private CityService cityService;

    /**
     * Crée un nouveau département.
     *
     * @param departement Le département à créer.
     * @return Le département créé.
     */
    @PostMapping
    public ResponseEntity<Departement> createDepartement(@RequestBody Departement departement) {
        Departement created = departementService.saveDepartement(departement);
        return ResponseEntity.ok(created);
    }

    /**
     * Récupère tous les départements.
     *
     * @return Une liste de tous les départements.
     */
    @GetMapping
    public ResponseEntity<List<Departement>> getAllDepartements() {
        List<Departement> departements = departementService.findAllDepartements();
        return ResponseEntity.ok(departements);
    }

    /**
     * Met à jour un département donné.
     *
     * @param id L'identifiant du département à mettre à jour.
     * @param departement Les nouvelles données pour le département.
     * @return Le département mis à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Departement> updateDepartement(@PathVariable Long id, @RequestBody Departement departement) {
        Departement updated = departementService.updateDepartement(id, departement);
        return ResponseEntity.ok(updated);
    }

    /**
     * Supprime un département.
     *
     * @param id L'identifiant du département à supprimer.
     * @return Un message de succès.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
        return ResponseEntity.ok("Departement deleted successfully");
    }


}
