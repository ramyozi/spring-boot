package fr.diginamic.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.diginamic.dto.CityDto;
import fr.diginamic.mapper.CityMapper;
import fr.diginamic.models.City;
import fr.diginamic.models.Departement;
import fr.diginamic.services.CityService;
import fr.diginamic.services.DepartementService;

@Controller
public class CityViewController {

	@Autowired
	private CityService cityService;
	@Autowired
	private DepartementService departementService;

	@GetMapping("/")
	public ModelAndView index() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Map<String, Object> model = new HashMap<>();
		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			String roles = authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.joining(", "));
			model.put("username", username);
			model.put("roles", roles);
		}
		return new ModelAndView("index", model);
	}

	@GetMapping("/city/list")
	public ModelAndView getCities() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Map<String, Object> model = new HashMap<>();
		model.put("cities", cityService.extractCities());
		String username = authentication.getName();
		String roles = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(", "));
		model.put("username", username);
		model.put("roles", roles);
		return new ModelAndView("city/list", model);
	}

	@GetMapping("/city/add")
	public ModelAndView addCityForm() {
		return new ModelAndView("city/create", "city", new CityDto());
	}

	@PostMapping("/city/add")
	public String addCity(@ModelAttribute CityDto cityDto) {
		try {
			City city = CityMapper.dtoToCity(cityDto, departementService);
			cityService.createCity(city);
			return "redirect:/city/list";
		} catch (Exception e) {
			return "city/create";
		}
	}

	@GetMapping("/departement/check")
	public ResponseEntity<?> checkDepartement(@RequestParam String code) {
		try {
			Departement departement = departementService
					.findDepartementByCode(code);
			if (departement != null) {
				return ResponseEntity.ok(departement);
			} else {
				return ResponseEntity.ok(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred");
		}
	}

	@PostMapping("/city/delete/{id}")
	public String deleteCity(@PathVariable int id) {
		cityService.deleteCity(id);
		return "redirect:/city/list";
	}
}
