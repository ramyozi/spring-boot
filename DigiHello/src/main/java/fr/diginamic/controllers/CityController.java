package fr.diginamic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.diginamic.dto.CityDto;
import fr.diginamic.models.City;
import fr.diginamic.services.CityService;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = cityService.extractCities();
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable int id) {
        CityDto cityDto = cityService.getCityDtoById(id);
        return cityDto != null ? ResponseEntity.ok(cityDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/name/{nom}")
    public ResponseEntity<City> getCityByName(@PathVariable String nom) {
        City city = cityService.extractCity(nom);
        return city != null ? ResponseEntity.ok(city) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City nouvelleCity) {
        City savedCity = cityService.createCity(nouvelleCity);
        return ResponseEntity.ok(savedCity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<City>> updateCity(@PathVariable int id, @RequestBody City cityModifiee) {
        List<City> cities = cityService.updateCity(id, cityModifiee);
        return ResponseEntity.ok(cities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<City>> deleteCity(@PathVariable int id) {
        List<City> cities = cityService.deleteCity(id);
        return ResponseEntity.ok(cities);
    }

    @PostMapping("/init")
    public ResponseEntity<String> initializeData() {
        cityService.initData();
        return ResponseEntity.ok("Data initialized successfully");
    }
}
