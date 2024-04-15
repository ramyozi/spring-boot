package controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.diginamic.services.CityService;
import fr.diginamic.models.City;
import fr.diginamic.controllers.CityController;
import fr.diginamic.dto.CityDto;

import org.mockito.ArgumentMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    private City paris;
    private CityDto parisDto;

    @BeforeEach
    void setup() {
        paris = new City("Paris", 2148000, null);
        parisDto = new CityDto();
        parisDto.setName("Paris");
        parisDto.setPopulation(2148000);
        parisDto.setDepartementCode("75");
        parisDto.setDepartementName("Paris");
    }

    @Test
    public void testGetCities() throws Exception {
        List<City> allCities = Arrays.asList(paris);
        given(cityService.extractCities()).willReturn(allCities);

        mockMvc.perform(get("/cities"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].name", is("Paris")));
    }

    @Test
    public void testGetCityById() throws Exception {
        given(cityService.getCityDtoById(1)).willReturn(parisDto);

        mockMvc.perform(get("/cities/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name", is("Paris")))
               .andExpect(jsonPath("$.population", is(2148000)));
    }

    @Test
    public void testGetCityByName() throws Exception {
        given(cityService.extractCity("Paris")).willReturn(paris);

        mockMvc.perform(get("/cities/name/Paris"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name", is("Paris")))
               .andExpect(jsonPath("$.population", is(2148000)));
    }

    @Test
    public void testCreateCity() throws Exception {
        given(cityService.createCity(ArgumentMatchers.any(City.class))).willReturn(paris);

        ObjectMapper objectMapper = new ObjectMapper();
        String cityJson = objectMapper.writeValueAsString(paris);

        mockMvc.perform(post("/cities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cityJson))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name", is("Paris")))
               .andExpect(jsonPath("$.population", is(2148000)));
    }

    @Test
    public void testUpdateCity() throws Exception {
        List<City> updatedCities = Arrays.asList(paris);
        given(cityService.updateCity(eq(1), ArgumentMatchers.any(City.class))).willReturn(updatedCities);

        ObjectMapper objectMapper = new ObjectMapper();
        String cityJson = objectMapper.writeValueAsString(paris);

        mockMvc.perform(put("/cities/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cityJson))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].name", is("Paris")));
    }

    @Test
    public void testDeleteCity() throws Exception {
        List<City> remainingCities = Arrays.asList();
        given(cityService.deleteCity(1)).willReturn(remainingCities);

        mockMvc.perform(delete("/cities/1"))
               .andExpect(status().isOk());
    }

    @Test
    public void testExportCitiesCsv() throws Exception {
        List<CityDto> cities = Arrays.asList(parisDto);
        given(cityService.getAllCityDtos()).willReturn(cities);

        mockMvc.perform(get("/cities/export"))
               .andExpect(status().isOk())
               .andExpect(content().contentType("text/csv"))
               .andExpect(header().string("Content-Disposition", "attachment; filename=cities.csv"));
    }

    @Test
    public void testExportCitiesPdf() throws Exception {
        List<CityDto> cities = Arrays.asList(parisDto);
        given(cityService.getAllCityDtos()).willReturn(cities);

        mockMvc.perform(get("/cities/export/pdf"))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/pdf"))
               .andExpect(header().string("Content-Disposition", "attachment; filename=cities.pdf"));
    }
}
