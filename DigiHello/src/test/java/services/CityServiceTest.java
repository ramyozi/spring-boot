package services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.diginamic.dto.CityDto;
import fr.diginamic.models.City;
import fr.diginamic.models.Departement;
import fr.diginamic.services.CityService;
import fr.diginamic.services.DepartementService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CityService.class})
public class CityServiceTest {

    @MockBean
    private EntityManager em;

    @MockBean
    private DepartementService departementService;

    @Autowired
    private CityService cityService;

    private City paris;
    private CityDto parisDto;
    private Departement departement;

    @BeforeEach
    void setup() {
        departement = new Departement("Paris", "75");
        paris = new City("Paris", 2148000, departement);
        parisDto = new CityDto();
        parisDto.setName("Paris");
        parisDto.setPopulation(2148000);
        parisDto.setDepartementCode("75");
        parisDto.setDepartementName("Paris");
    }

    @Test
    public void testExtractCities() {
        TypedQuery<City> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT c FROM City c", City.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(paris));

        List<City> result = cityService.extractCities();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Paris", result.get(0).getName());
    }

    @Test
    public void testGetCityDtoById() {
        when(em.find(City.class, 1)).thenReturn(paris);

        CityDto result = cityService.getCityDtoById(1);
        assertNotNull(result);
        assertEquals("Paris", result.getName());
    }

    @Test
    public void testCreateCity() {
        when(departementService.findDepartementByCode("75")).thenReturn(departement);

        City result = cityService.createCity(paris);
        assertNotNull(result);
        verify(em, times(1)).persist(paris);
    }

    @Test
    public void testUpdateCity() {
        when(em.find(City.class, 1)).thenReturn(paris);
        TypedQuery<City> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT c FROM City c", City.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(paris));

        List<City> result = cityService.updateCity(1, paris);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(em, times(1)).merge(paris);
    }

    @Test
    public void testDeleteCity() {
        when(em.find(City.class, 1)).thenReturn(paris);
        TypedQuery<City> query = mock(TypedQuery.class);
        when(em.createQuery("SELECT c FROM City c", City.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList());

        List<City> result = cityService.deleteCity(1);
        assertTrue(result.isEmpty());
        verify(em, times(1)).remove(paris);
    }

    @Test
    public void testInitData() {
        doNothing().when(departementService).saveDepartement(any(Departement.class));
        doReturn(paris).when(cityService).createCity(any(City.class));

        assertDoesNotThrow(() -> cityService.initData());
        verify(cityService, times(10)).createCity(any(City.class));
    }

    @Test
    public void testGetTopNCitiesByDepartment() {
        TypedQuery<City> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(City.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(paris));

        List<City> result = cityService.getTopNCitiesByDepartment("75", 1);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Paris", result.get(0).getName());
    }

}
