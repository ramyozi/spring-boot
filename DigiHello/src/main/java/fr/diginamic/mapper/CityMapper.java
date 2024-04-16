package fr.diginamic.mapper;

import fr.diginamic.dto.CityDto;
import fr.diginamic.models.City;
import fr.diginamic.models.Departement;
import fr.diginamic.services.DepartementService;

public class CityMapper {

    public static CityDto cityToDto(City city) {
        CityDto dto = new CityDto();
        dto.setName(city.getName());
        dto.setPopulation(city.getPopulation());
        dto.setDepartementCode(city.getDepartement().getCode());
        dto.setDepartementName(city.getDepartement().getName());
        return dto;
    }
    
    public static City dtoToCity(CityDto cityDto, DepartementService departementService) {
        City city = new City();
        city.setName(cityDto.getName());
        city.setPopulation(cityDto.getPopulation());
        
        Departement d = departementService.findDepartementByCode(cityDto.getDepartementCode());
        city.setDepartement(d);
        return city;
    }
}
