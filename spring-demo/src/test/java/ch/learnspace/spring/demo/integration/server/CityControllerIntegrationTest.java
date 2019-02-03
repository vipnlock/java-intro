package ch.learnspace.spring.demo.integration.server;

import static ch.learnspace.spring.demo.setup.helper.TestObjectCreator.NOT_EXISTING_COUNTRY_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ch.learnspace.spring.demo.data.repositiory.CityRepository;
import ch.learnspace.spring.demo.dto.converter.CityConverter;
import ch.learnspace.spring.demo.dto.entity.CityDto;
import ch.learnspace.spring.demo.mvc.rest.client.RestClient;
import ch.learnspace.spring.demo.mvc.rest.client.RestClientException;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;
import lombok.var;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CityControllerIntegrationTest {
    
    @Autowired
    private RestClient restClient;

    @LocalServerPort
    private int port;
    private String urlPrefix;

    @MockBean
    private CityRepository cityRepository;

    @PostConstruct
    private void setup() {
        urlPrefix = "http://localhost:" + port;
    }

    @Test
    @DisplayName("Call '/api/world/city/{id}' with valid ID")
    void getCity_validId() {
        var city = TestObjectCreator.createRandomCity();
        Mockito.when(cityRepository.findById(city.getId())).thenReturn(Optional.of(city));
        
        String url = urlPrefix + "/api/world/city/" + city.getId();
        ResponseEntity<CityDto> response = null; // DTO! Not entity anymore!
        try {
            response = restClient.postForEntity(url, new CityDto(), CityDto.class);
        } catch (RestClientException e) {
            fail("Exception occurred: " + e.getMessage());
        }

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(city, CityConverter.convert(response.getBody()));
        assertNotSame(city, CityConverter.convert(response.getBody()));
    }

    @Test
    @DisplayName("Call '/api/world/city/{id}' with invalid ID")
    void getCity_invalidId() {
        final int cityId = new Random().nextInt();
        String url = urlPrefix + "/api/world/city/" + cityId;

        var ex = assertThrows(RestClientException.class,
                () -> restClient.postForEntity(url, new CityDto(), CityDto.class));
        assertEquals("City not found with ID = " + cityId, ex.getMessage());
    }

    @Test
    @DisplayName("Call '/api/world/city/country/{countrycode}' with valid code")
    void getCitiesForCountryCode_validCode() {
        var countryCode = "CCC";
        var cities = Stream.generate(TestObjectCreator::createRandomCity)
                           .limit(10)
                           .collect(Collectors.toList());
        Mockito.when(cityRepository.findCitiesByCountrycode(countryCode)).thenReturn(cities);
        
        String url = urlPrefix + "/api/world/city/country/" + countryCode;

        ResponseEntity<CityDto[]> response = null; // DTO! Not entity anymore!
        try {
            response = restClient.getForEntity(url, CityDto[].class);
        } catch (RestClientException e) {
            fail("Exception occurred: " + e.getMessage());
        }

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());

        assertEquals(cities, Arrays.stream(response.getBody())
                                   .map(CityConverter::convert)
                                   .collect(Collectors.toList()));
    }

    @Test
    @DisplayName("Call '/api/world/city/country/{countrycode}' with invalid code")
    void getCitiesForCountryCode_invalidCode() {
        String url = urlPrefix + "/api/world/city/country/" + NOT_EXISTING_COUNTRY_CODE;

        var ex = assertThrows(RestClientException.class, () -> restClient.getForEntity(url, CityDto[].class));

        assertEquals("Cities not found for county code = " + NOT_EXISTING_COUNTRY_CODE, ex.getMessage());
    }
    
    @Test
    @DisplayName("Call '/api/world/city/country/{countrycode}' with invalid State")
    void getCitiesForCountryCode_invalidState() {
        var url = urlPrefix + "/api/world/city/country/" + NOT_EXISTING_COUNTRY_CODE;
        var message = TestObjectCreator.createRandomString(15);
        Mockito.when(cityRepository.findCitiesByCountrycode(anyString())).thenThrow(new IllegalStateException(message));

        var ex = assertThrows(RestClientException.class, () -> restClient.getForEntity(url, CityDto[].class));

        assertEquals(message, ex.getMessage());
    }

}
