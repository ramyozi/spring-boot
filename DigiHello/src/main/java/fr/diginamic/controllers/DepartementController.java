package fr.diginamic.controllers;

import fr.diginamic.dto.DepartementDto;
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

    @PostMapping
    public ResponseEntity<Departement> createDepartement(@RequestBody Departement departement) {
        Departement created = departementService.saveDepartement(departement);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{code}")
    public ResponseEntity<DepartementDto> getDepartementByCode(@PathVariable String code) {
        DepartementDto departementDto = departementService.getDepartementDtoByCode(code);
        return departementDto != null ? ResponseEntity.ok(departementDto) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<DepartementDto>> getAllDepartements() {
        List<DepartementDto> departements = departementService.getAllDepartementDtos();
        return ResponseEntity.ok(departements);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Departement> updateDepartement(@PathVariable String code, @RequestBody Departement departement) {
        Departement updated = departementService.updateDepartementByCode(code, departement);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteDepartement(@PathVariable String code) {
        boolean deleted = departementService.deleteDepartementByCode(code);
        return deleted ? ResponseEntity.ok("Departement deleted successfully") : ResponseEntity.notFound().build();
    }
}
