package fr.diginamic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import fr.diginamic.dto.DepartementDto;
import fr.diginamic.models.Departement;
import fr.diginamic.services.DepartementService;

@Controller
public class DepartementViewController {
	
	@Autowired
    private DepartementService departementService;


	@PostMapping("/departement/create")
    public ResponseEntity<?> createDepartement(@RequestBody DepartementDto departementDto) {
        try {
            Departement existingDepartement = departementService.findDepartementByCode(departementDto.getCode());
            if (existingDepartement != null) {
                return ResponseEntity.ok(existingDepartement);
            }

            Departement newDepartement = new Departement(departementDto.getName(), departementDto.getCode());
            Departement savedDepartement = departementService.saveDepartement(newDepartement);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartement);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
