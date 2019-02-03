package ch.learnspace.spring.demo.mvc.rest.controller;

import ch.learnspace.spring.demo.data.entity.City;
import ch.learnspace.spring.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/world/city")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/{id}")
    public City getCity(@PathVariable Long id) {
        return cityService.getCity(id);
    }

    @GetMapping("/country/{countrycode}")
    public List<City> getCitiesForCountryCode(@PathVariable String countrycode) {
        return cityService.findCitiesByCountrycode(countrycode.toUpperCase());
    }

    @GetMapping("/aop/random")
    public City getRandomCity() {
        return cityService.getRandomCity();
    }

    @GetMapping("/aop/dummy")
    public City getDummyCity() {
        return cityService.getDummyCity();
    }

    @GetMapping("/aop/exception")
    public City getExceptionCity() {
        return cityService.getExceptionCity();
    }
}
