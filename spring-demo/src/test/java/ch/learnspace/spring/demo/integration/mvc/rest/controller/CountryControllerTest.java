package ch.learnspace.spring.demo.integration.mvc.rest.controller;

import static ch.learnspace.spring.demo.setup.helper.TestObjectCreator.NOT_EXISTING_COUNTRY_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.learnspace.spring.demo.data.entity.Country;
import ch.learnspace.spring.demo.data.repositiory.CountryRepository;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("findById with valid code")
    void findById_validCode() throws Exception {
        final Country testCountry = TestObjectCreator.createRandomCountry();
        countryRepository.save(testCountry);

        final String json = mockMvc.perform(get("/api/world/country/" + testCountry.getCode()))
                             .andExpect(MockMvcResultMatchers.status().isOk())
                             .andReturn().getResponse().getContentAsString();
        final Country foundCountry = new ObjectMapper().readValue(json, Country.class);

        assertThat(foundCountry.getName()).isEqualTo(testCountry.getName());
    }

    @Test
    @DisplayName("findById with invalid code")
    void findById_invalidCode() throws Exception {
        String exceptionMessage = mockMvc.perform(get("/api/world/country/" + NOT_EXISTING_COUNTRY_CODE))
                                         .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                         .andReturn().getResponse().getContentAsString();
        assertThat(exceptionMessage).isEqualTo("Country not found with Code = " + NOT_EXISTING_COUNTRY_CODE);
    }
    
    @Test
    @DisplayName("findCountriesLike with valid name")
    void findCountriesLike_validName() throws Exception {
        final String countriesCommonName = "Very Common Name";

        final Collection<Country> testCountries = Stream.generate(TestObjectCreator::createRandomCountry)
                                                        .limit(10)
                                                        .peek(country -> country.setName(countriesCommonName))
                                                        .collect(Collectors.toList());
        Iterable<Country> savedCountries = countryRepository.saveAll(testCountries);

        String json = mockMvc.perform(get("/api/world/country/name/" + countriesCommonName))
                             .andExpect(MockMvcResultMatchers.status().isOk())
                             .andReturn().getResponse().getContentAsString();

        Country[] foundCountries = new ObjectMapper().readValue(json, Country[].class);
        assertNotNull(foundCountries);
        assertThat(foundCountries).hasSize(10);
        assertThat(foundCountries).containsAll(savedCountries);
    }

    @Test
    @DisplayName("findCountriesLike with invalid name")
    void findCountriesLike_invalidName() throws Exception {
        String exceptionMessage = mockMvc.perform(get("/api/world/country/name/" + NOT_EXISTING_COUNTRY_CODE))
                                         .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                         .andReturn().getResponse().getContentAsString();
        assertThat(exceptionMessage).isEqualTo("Country not found like = " + NOT_EXISTING_COUNTRY_CODE);
    }

    @Test
    @DisplayName("findCountriesLike with too short name")
    void findCountriesLike_invalidSnippet() throws Exception {
        final String tooShortName = TestObjectCreator.createRandomString(2);

        String exceptionMessage = mockMvc.perform(get("/api/world/country/name/" + tooShortName))
                                         .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                         .andReturn().getResponse().getContentAsString();
        assertThat(exceptionMessage).isEqualTo("Snippet must have at least 3 characters.");
    }

}
