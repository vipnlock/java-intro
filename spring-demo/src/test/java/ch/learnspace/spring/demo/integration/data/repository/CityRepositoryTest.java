package ch.learnspace.spring.demo.integration.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ch.learnspace.spring.demo.data.entity.City;
import ch.learnspace.spring.demo.data.repositiory.CityRepository;

/*
 * === 1. Integration Testing the Data Layer with @DataJpaTest
 *
 * @DataJpaTest goes up the package structure until it finds a class annotated with @SpringBootConfiguration.
 * It then adds all Spring Data repositories within that package and all sub-packages to the application context,
 * so that we can just autowire them and run tests against them.
 *
 * see https://reflectoring.io/testing-verticals-and-layers-spring-boot/
 *
 * Since we have a @SpringBootConfiguration for each of our vertical modules,
 * only the repositories of the one module we’re currently testing will be loaded,
 * effectively decoupling our modules even in our tests.
 *
 * A note on testing repositories:
 * tests for repository methods only make sense for custom queries annotated with the @Query annotation.
 * We don’t need to test repository methods that make use of Spring Data’s naming conventions since the
 * application won’t start if we’re not following the naming conventions. If we have any test that starts
 * the application context, it will fail and notify us about our error.
 */
@DataJpaTest
public class CityRepositoryTest {

    private final static String COUNTRY_CODE = "CCC";

    @Autowired
    private CityRepository cityRepository;

    @Test
    void findByCountryCode() {
        City city = City.builder()
                        .id(1L)
                        .name("CityName")
                        .countrycode(COUNTRY_CODE)
                        .build();
        cityRepository.save(city);

        List<City> foundCities = cityRepository.findCitiesByCountrycode(COUNTRY_CODE);
        assertThat(foundCities).hasSize(1);
        City foundCity = foundCities.get(0);
        assertThat(foundCity.getName()).isEqualTo("CityName");
    }

}
