package fr.diginamic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.models.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {
	
	private final List<City> cities = new ArrayList<>();

    public CityController() {
        // Initialisation avec quelques villes à des fins de test
        cities.add(new City("Paris", 2148000));
        cities.add(new City("Lyon", 513275));
    }

    // Ma requete de fetch
    @GetMapping
    public List<City> getCities() {
        return cities;
    }

    // Requete de création
    @PostMapping
    public ResponseEntity<String> addCity(@RequestBody City newCity) {
        Optional<City> existingCity = cities.stream().filter(city -> city.getName().equalsIgnoreCase(newCity.getName())).findAny();
        if (existingCity.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La ville existe déjà");
        } else {
            cities.add(newCity);
            return ResponseEntity.ok("Ville insérée avec succès");
        }
    }
}


