package ch.learnspace.spring.demo.unittests.service;

import static ch.learnspace.spring.demo.setup.helper.TestObjectCreator.NOT_EXISTING_COUNTRY_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.learnspace.spring.demo.data.repositiory.CityRepository;
import ch.learnspace.spring.demo.service.CityService;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;
import lombok.var;

/*
 * === 5. Testing single Beans with Plain Old Unit Tests.
 *
 * All Spring Boot test features discussed above support integration tests and not unit tests.
 * To implement a plain old unit test of any bean, we should actually refrain from using
 * the Spring Boot features, because they add a significant overhead to our tests by creating
 * an application context.
 * Expecially in our business layer we want to test business logic and not integration with
 * the web or data layer.
 */
@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    private CityService cityService;

    @BeforeEach
    void setup() {
        this.cityService = new CityService(cityRepository);
    }

    @Test
    @DisplayName("getCity with valid ID")
    void getCity_validId() {
        var city = TestObjectCreator.createRandomCity();
        Mockito.when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));

        assertEquals(city, cityService.getCity(city.getId()));
    }

    @Test
    @DisplayName("getCity with invalid ID")
    void getCity_invalidId() {
        final Long cityId = TestObjectCreator.createRandomCity().getId();
        var ex = assertThrows(IllegalArgumentException.class, () -> cityService.getCity(cityId));

        assertEquals("City not found with ID = " + cityId, ex.getMessage());
    }

    @Test
    @DisplayName("findCitiesByCountrycode with valid code")
    void findCitiesByCountrycode_with_validCode() {
        var countryCode = "CCC";

        var cities = Stream.generate(TestObjectCreator::createRandomCity)
                           .limit(10)
                           .collect(Collectors.toList());
        Mockito.when(cityRepository.findCitiesByCountrycode(countryCode)).thenReturn(cities);

        var result = cityService.findCitiesByCountrycode(countryCode);
        assertEquals(cities, result);
    }

    @Test
    @DisplayName("findCitiesByCountrycode with invalid code")
    void findCitiesByCountrycode_with_invalidCode() {
        var ex = assertThrows(IllegalArgumentException.class, () ->
                cityService.findCitiesByCountrycode(NOT_EXISTING_COUNTRY_CODE));

        assertEquals("Cities not found for county code = " + NOT_EXISTING_COUNTRY_CODE, ex.getMessage());
    }

}