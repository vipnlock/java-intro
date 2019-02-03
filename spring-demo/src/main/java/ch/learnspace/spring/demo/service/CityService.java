package ch.learnspace.spring.demo.service;

import ch.learnspace.spring.demo.data.entity.City;
import ch.learnspace.spring.demo.data.repositiory.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CityService {

    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getCity(Long id) {
        return cityRepository.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("City not found with ID = " + id));
    }

    public List<City> findCitiesByCountrycode(String countryCode) {
        List<City> cities = cityRepository.findCitiesByCountrycode(countryCode);
        Assert.notEmpty(cities, "Cities not found for county code = " + countryCode);
        return cities;
    }

    public City getDummyCity() {
        return City.builder()
                   .name("Dummy")
                   .build();
    }

    public City getRandomCity() {
        long numberOfEntries = cityRepository.count();
        long randomId = (long) (Math.random() * numberOfEntries);
        return cityRepository.findById(randomId).orElseThrow(
                () -> new IllegalArgumentException("City not found with ID = " + randomId));
    }

    public City getExceptionCity() {
        throw new UnsupportedOperationException("Better do an implementation here!");
    }
}
