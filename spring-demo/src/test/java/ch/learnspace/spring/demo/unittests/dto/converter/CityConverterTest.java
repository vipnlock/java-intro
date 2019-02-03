package ch.learnspace.spring.demo.unittests.dto.converter;

import ch.learnspace.spring.demo.data.entity.City;
import ch.learnspace.spring.demo.dto.converter.CityConverter;
import ch.learnspace.spring.demo.dto.entity.CityDto;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;
import lombok.var;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityConverterTest {
    
    @Test
    void convert_toDto() {
        var city = TestObjectCreator.createRandomCity();
        var result = CityConverter.convert(city);
        assertTrue(result instanceof CityDto);
        assertEquals(city.getId(), result.getId());
        assertEquals(city.getName(), result.getName());
        assertEquals(city.getCountrycode(), result.getCountrycode());
        assertEquals(city.getDistrict(), result.getDistrict());
        assertEquals(city.getPopulation(), result.getPopulation());
        assertEquals(city, CityConverter.convert(result));
    }
    
    @Test
    void convert_toDbEntity() {
        var cityDto = TestObjectCreator.createRandomCityDto();
        var result = CityConverter.convert(cityDto);
        assertTrue(result instanceof City);
        assertEquals(cityDto.getId(), result.getId());
        assertEquals(cityDto.getName(), result.getName());
        assertEquals(cityDto.getCountrycode(), result.getCountrycode());
        assertEquals(cityDto.getDistrict(), result.getDistrict());
        assertEquals(cityDto.getPopulation(), result.getPopulation());
        assertEquals(cityDto, CityConverter.convert(result));
    }
}