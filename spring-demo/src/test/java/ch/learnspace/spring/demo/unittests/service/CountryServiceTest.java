package ch.learnspace.spring.demo.unittests.service;

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

import ch.learnspace.spring.demo.data.repositiory.CountryRepository;
import ch.learnspace.spring.demo.service.CountryService;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;
import lombok.var;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    private CountryService countryService;

    @BeforeEach
    void setup() {
        this.countryService = new CountryService(countryRepository);
    }

    @Test
    @DisplayName("findById with valid code")
    void findyById_validCode() {
        var country = TestObjectCreator.createRandomCountry();
        Mockito.when(countryRepository.findById(country.getCode())).thenReturn(Optional.of(country));
        assertEquals(country, countryService.findyById(country.getCode()));
    }

    @Test
    @DisplayName("findById with invalid code")
    void findyById_invalidCode() {
        var country = TestObjectCreator.createRandomCountry();

        var ex = assertThrows(IllegalArgumentException.class, () -> countryService.findyById(country.getCode()));

        assertEquals("Country not found with Code = " + country.getCode(), ex.getMessage());
    }

    @Test
    @DisplayName("findCountriesLike with valid name")
    void findCountriesLike_validName() {
        var referenceList = Stream.generate(TestObjectCreator::createRandomCountry)
                                  .limit(10)
                                  .collect(Collectors.toList());
        var input = TestObjectCreator.createRandomString(3);

        Mockito.when(countryRepository.findAllLike(input)).thenReturn(referenceList);

        var result = countryService.findCountriesLike(input);
        assertEquals(referenceList, result);
    }

    @Test
    @DisplayName("findCountriesLike with invalid name")
    void findCountriesLike_invalidName() {
        final String name = TestObjectCreator.createRandomString(3);

        var ex = assertThrows(IllegalArgumentException.class, () -> countryService.findCountriesLike(name));

        assertEquals("Country not found like = " + name, ex.getMessage());
    }

    @Test
    @DisplayName("findCountriesLike with too short name")
    void findCountriesLike_invalidSnippet() {
        final String name = TestObjectCreator.createRandomString(2);

        var ex = assertThrows(IllegalArgumentException.class, () -> countryService.findCountriesLike(name));

        assertEquals("Snippet must have at least 3 characters.", ex.getMessage());
    }

}