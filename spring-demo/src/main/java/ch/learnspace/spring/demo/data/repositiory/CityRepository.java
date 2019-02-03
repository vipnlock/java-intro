package ch.learnspace.spring.demo.data.repositiory;

import ch.learnspace.spring.demo.data.entity.City;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Long> {

    List<City> findCitiesByCountrycode(String countrycode);
}
