package ch.learnspace.spring.demo.mvc.rest.controller;

import ch.learnspace.spring.demo.data.entity.Country;
import ch.learnspace.spring.demo.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/world/country")
public class CountryController {

    private CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{code}")
    public Country findBy(@PathVariable String code) {
        return countryService.findyById(code.toUpperCase());
    }

    @GetMapping("/name/{name}")
    public List<Country> findCountriesLike(@PathVariable String name) {
        return countryService.findCountriesLike(name);
    }
}
