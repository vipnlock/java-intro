package ch.learnspace.spring.demo.unittests.dto.converter;

import ch.learnspace.spring.demo.dto.converter.CountryConverter;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;
import lombok.var;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryConverterTest {
    
    @Test
    void convert_toDto() {
        var country = TestObjectCreator.createRandomCountry();
        var result = CountryConverter.convert(country);
        assertEquals(country.getCode(), result.getCode());
        assertEquals(country.getName(), result.getName());
        assertEquals(country.getContinent(), result.getContinent());
        assertEquals(country.getRegion(), result.getRegion());
        assertEquals(country.getSurfacearea(), result.getSurfacearea());
        assertEquals(country.getIndepyear(), result.getIndepyear());
        assertEquals(country.getPopulation(), result.getPopulation());
        assertEquals(country.getLifeexpectancy(), result.getLifeexpectancy());
        assertEquals(country.getGnp(), result.getGnp());
        assertEquals(country.getGnpold(), result.getGnpold());
        assertEquals(country.getLocalname(), result.getLocalname());
        assertEquals(country.getGovernmentform(), result.getGovernmentform());
        assertEquals(country.getHeadofstate(), result.getHeadofstate());
        assertEquals(country.getCapital(), result.getCapital());
        assertEquals(country.getCode2(), result.getCode2());
        assertEquals(country, CountryConverter.convert(result));
    }
    
    @Test
    void convert_toDbEntity() {
        var countryDto = TestObjectCreator.createRandomCountryDto();
        var result = CountryConverter.convert(countryDto);
        assertEquals(countryDto.getCode(), result.getCode());
        assertEquals(countryDto.getName(), result.getName());
        assertEquals(countryDto.getContinent(), result.getContinent());
        assertEquals(countryDto.getRegion(), result.getRegion());
        assertEquals(countryDto.getSurfacearea(), result.getSurfacearea());
        assertEquals(countryDto.getIndepyear(), result.getIndepyear());
        assertEquals(countryDto.getPopulation(), result.getPopulation());
        assertEquals(countryDto.getLifeexpectancy(), result.getLifeexpectancy());
        assertEquals(countryDto.getGnp(), result.getGnp());
        assertEquals(countryDto.getGnpold(), result.getGnpold());
        assertEquals(countryDto.getLocalname(), result.getLocalname());
        assertEquals(countryDto.getGovernmentform(), result.getGovernmentform());
        assertEquals(countryDto.getHeadofstate(), result.getHeadofstate());
        assertEquals(countryDto.getCapital(), result.getCapital());
        assertEquals(countryDto.getCode2(), result.getCode2());
        assertEquals(countryDto, CountryConverter.convert(result));
    }
}