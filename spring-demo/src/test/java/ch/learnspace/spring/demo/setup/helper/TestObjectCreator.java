package ch.learnspace.spring.demo.setup.helper;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.Random;

import ch.learnspace.spring.demo.data.entity.City;
import ch.learnspace.spring.demo.data.entity.Country;
import ch.learnspace.spring.demo.data.entity.Language;
import ch.learnspace.spring.demo.dto.entity.CityDto;
import ch.learnspace.spring.demo.dto.entity.CountryDto;
import ch.learnspace.spring.demo.dto.entity.LanguageDto;

public final class TestObjectCreator {

    public static final String NOT_EXISTING_COUNTRY_CODE = "NEC";

    public static City createRandomCity() {
        Random random = new Random();
        return City.builder()
                   .id(random.nextLong())
                   .name(randomAlphabetic(10))
                   .countrycode(randomAlphabetic(3).toUpperCase())
                   .district(randomAlphabetic(2).toUpperCase())
                   .population(random.nextInt(100000000))
                   .build();
    }
    
    public static CityDto createRandomCityDto() {
        Random random = new Random();
        return CityDto.builder()
                      .id(random.nextLong())
                      .name(randomAlphabetic(10))
                      .countrycode(randomAlphabetic(3).toUpperCase())
                      .district(randomAlphabetic(2).toUpperCase())
                      .population(random.nextInt(100000000))
                      .build();
    }
    
    public static Country createRandomCountry() {
        Random random = new Random();
        return Country.builder()
                      .code(randomAlphabetic(3).toUpperCase())
                      .name(randomAlphabetic(1).toUpperCase() + randomAlphabetic(9).toLowerCase())
                      .continent(randomAlphabetic(1).toUpperCase() + randomAlphabetic(8).toLowerCase())
                      .region(randomAlphabetic(1).toUpperCase() + randomAlphabetic(7).toLowerCase())
                      .surfacearea(random.nextDouble())
                      .indepyear(1900 + random.nextInt(99))
                      .population(random.nextInt())
                      .lifeexpectancy(random.nextDouble())
                      .gnp(random.nextDouble())
                      .gnpold(random.nextDouble())
                      .localname(randomAlphabetic(1).toUpperCase() + randomAlphabetic(6).toLowerCase())
                      .governmentform(randomAlphabetic(1).toUpperCase() + randomAlphabetic(5).toLowerCase())
                      .headofstate(randomAlphabetic(1).toUpperCase() + randomAlphabetic(4).toLowerCase())
                      .capital(random.nextInt())
                      .code2(randomAlphabetic(1).toUpperCase() + randomAlphabetic(3).toLowerCase())
                      .build();
    }
    
    public static CountryDto createRandomCountryDto() {
        Random random = new Random();
        return CountryDto.builder()
                         .code(randomAlphabetic(3).toUpperCase())
                         .name(randomAlphabetic(1).toUpperCase() + randomAlphabetic(9).toLowerCase())
                         .continent(randomAlphabetic(1).toUpperCase() + randomAlphabetic(8).toLowerCase())
                         .region(randomAlphabetic(1).toUpperCase() + randomAlphabetic(7).toLowerCase())
                         .surfacearea(random.nextDouble())
                         .indepyear(1900 + random.nextInt(99))
                         .population(random.nextInt())
                         .lifeexpectancy(random.nextDouble())
                         .gnp(random.nextDouble())
                         .gnpold(random.nextDouble())
                         .localname(randomAlphabetic(1).toUpperCase() + randomAlphabetic(6).toLowerCase())
                         .governmentform(randomAlphabetic(1).toUpperCase() + randomAlphabetic(5).toLowerCase())
                         .headofstate(randomAlphabetic(1).toUpperCase() + randomAlphabetic(4).toLowerCase())
                         .capital(random.nextInt())
                         .code2(randomAlphabetic(1).toUpperCase() + randomAlphabetic(3).toLowerCase())
                         .build();
    }
    
    public static Language createRandomLanguage() {
        Random random = new Random();
        return Language.builder()
                       .countrycode(randomAlphabetic(3).toUpperCase())
                       .language(randomAlphabetic(1).toUpperCase() + randomAlphabetic(9).toLowerCase())
                       .isofficial(random.nextBoolean())
                       .percentage(random.nextDouble())
                       .build();
    }
    
    public static LanguageDto createRandomLanguageDto() {
        Random random = new Random();
        return LanguageDto.builder()
                          .countrycode(randomAlphabetic(3).toUpperCase())
                          .language(randomAlphabetic(1).toUpperCase() + randomAlphabetic(9).toLowerCase())
                          .isofficial(random.nextBoolean())
                          .percentage(random.nextDouble())
                          .build();
    }
    
    public static String createRandomString(int length) {
        return randomAlphabetic(length);
    }
}
