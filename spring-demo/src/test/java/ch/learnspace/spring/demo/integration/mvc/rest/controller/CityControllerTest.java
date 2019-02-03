package ch.learnspace.spring.demo.integration.mvc.rest.controller;

import static ch.learnspace.spring.demo.setup.helper.TestObjectCreator.NOT_EXISTING_COUNTRY_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.learnspace.spring.demo.data.entity.City;
import ch.learnspace.spring.demo.data.repositiory.CityRepository;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;

/*
 * === 4. Integration Testing a Vertical Module using @SpringBootTest.
 *
 * If we put a test annotated with @SpringBootTest into the main package of one
 * of our modules, * it will create the part of the application context needed by this module
 * (again, because our module contains a @SpringBootConfiguration).
 *
 * Doing this, we can now create integration tests between any beans from the application context.
 * For example, we can create a test that goes through all layers of our module from web down to the database.
 */
@SpringBootTest
class CityControllerTest {

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private CityRepository cityRepository;

    private MockMvc mockMvc;

    /*
     * Since we’re not using @WebMvcTest here, we have to create a MockMvc ourselves.
     */
    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    @DisplayName("getCity with valid ID")
    void getCity_validId() throws Exception {
        final City testCity = TestObjectCreator.createRandomCity();
        cityRepository.save(testCity);

        String json = mockMvc.perform(post("/api/world/city/" + testCity.getId()))
                             .andExpect(MockMvcResultMatchers.status().isOk())
                             .andReturn().getResponse().getContentAsString();
        City foundCity = new ObjectMapper().readValue(json, City.class);

        // AspectConfig makes it upper case
        assertThat(foundCity.getName()).isEqualTo(testCity.getName().toUpperCase());
    }
    
    @Test
    @DisplayName("getCity with invalid ID")
    void getCity_invalidId() throws Exception {
        final Long cityId = TestObjectCreator.createRandomCity().getId();

        String exceptionMessage = mockMvc.perform(post("/api/world/city/" + cityId))
                             .andExpect(MockMvcResultMatchers.status().isBadRequest())
                             .andReturn().getResponse().getContentAsString();
        assertThat(exceptionMessage).isEqualTo("City not found with ID = " + cityId);
    }

    @Test
    @DisplayName("getCitiesForCountryCode with valid Code")
    void getCitiesForCountryCode_validCode() throws Exception {
        final String testCountryCode = "CCC";

        final Collection<City> testCities = Stream.generate(TestObjectCreator::createRandomCity)
                .limit(10)
                .peek(city -> city.setCountrycode(testCountryCode))
                .collect(Collectors.toList());
        Iterable<City> savedCities = cityRepository.saveAll(testCities);

        String json = mockMvc.perform(get("/api/world/city/country/" + testCountryCode))
                             .andExpect(MockMvcResultMatchers.status().isOk())
                             .andReturn().getResponse().getContentAsString();

        City[] foundCities = new ObjectMapper().readValue(json, City[].class);
        assertNotNull(foundCities);
        assertThat(foundCities).hasSize(10);
        assertThat(foundCities).containsAll(savedCities);
    }

    @Test
    @DisplayName("getCitiesForCountryCode with invalid Code")
    void getCitiesForCountryCode_invalidCode() throws Exception {
        String exceptionMessage = mockMvc.perform(get("/api/world/city/country/" + NOT_EXISTING_COUNTRY_CODE))
                                         .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                         .andReturn().getResponse().getContentAsString();
        assertThat(exceptionMessage).isEqualTo("Cities not found for county code = " + NOT_EXISTING_COUNTRY_CODE);
    }
    
}