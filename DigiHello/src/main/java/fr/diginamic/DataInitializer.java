package fr.diginamic;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.models.City;
import fr.diginamic.models.Departement;
import fr.diginamic.services.CityService;
import fr.diginamic.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CityService cityService;

    @Autowired
    private DepartementService departementService;

    @Override
    public void run(String... args) throws Exception {
//        // Charger le fichier JSON
//        InputStream inputStream = new ClassPathResource("cities.json").getInputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, List<Map<String, Object>>> data = mapper.readValue(inputStream, Map.class);
//
//        List<Map<String, Object>> cities = data.get("cities");
//        for (Map<String, Object> cityInfo : cities) {
//            String departmentName = (String) cityInfo.get("department_name");
//            
//            String zipCode = (String) cityInfo.get("zip_code");
//            // Get the department code by extracting the first 2 digits of the zip code
//            //String departmentNumber = zipCode.substring(0, 2);
//            
//            String departmentNumber = (String) cityInfo.get("department_number");
//
//
//            String cityName = (String) cityInfo.get("label");
//
//            // Vérifier si le département existe déjà, sinon créer un nouveau
//            Departement department = departementService.findDepartementByCode(departmentNumber);
//            if (department == null) {
//                department = new Departement(departmentName, departmentNumber);
//                departementService.saveDepartement(department);
//            }
//
//            City existingCity = null;
//            try {
//                existingCity = cityService.extractCity(cityName);
//            } catch (Exception e) {
//                // Exception is expected if the city does not exist
//            }
//            
////            if (existingCity == null || !existingCity.getDepartement().getCode().equals(departmentNumber)) {
////                City city = new City();
////                city.setName(cityName);
////                city.setPopulation(0);  // Assigner la population à 0 si elle n'est pas disponible
////                city.setDepartement(department);
////                cityService.createCity(city);
////            } 
//        }
    	System.out.println();
    }
   
}
