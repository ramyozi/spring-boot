package fr.diginamic.mapper;

import fr.diginamic.models.City;
import fr.diginamic.dto.CityDto;

public class CityMapper {

    public static CityDto cityToDto(City city) {
        CityDto dto = new CityDto();
        dto.setName(city.getName());
        dto.setPopulation(city.getPopulation());
        dto.setDepartementCode(city.getDepartement().getCode());
        dto.setDepartementName(city.getDepartement().getName());
        return dto;
    }
}
