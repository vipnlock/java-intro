package ch.learnspace.spring.demo.service;

import ch.learnspace.spring.demo.data.entity.Country;
import ch.learnspace.spring.demo.data.repositiory.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CountryService {
    private CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country findyById(String code) {
        return countryRepository.findById(code.toUpperCase())
                                .orElseThrow(() -> new IllegalArgumentException("Country not found with Code = " + code));
    }

    public List<Country> findCountriesLike(String name) {
        if (name.length() < 3) {
            throw new IllegalArgumentException("Snippet must have at least 3 characters.");
        }
        List<Country> result = countryRepository.findAllLike(name);
        Assert.notEmpty(result, "Country not found like = " + name);
        return result;
    }

}
