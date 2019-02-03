package ch.learnspace.spring.demo.service;

import ch.learnspace.spring.demo.data.entity.Language;
import ch.learnspace.spring.demo.data.repositiory.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class LanguageService {
    
    private LanguageRepository languageRepository;
    
    @Autowired
    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }
    
    public List<Language> findByLanguage(String language) {
        List<Language> languages = languageRepository.findByLanguage(language.toLowerCase());
        Assert.notEmpty(languages, "Country language not found for language = " + language);
        return languages;
    }
    
    public List<Language> findByCountryCode(String countrycode) {
        Assert.isTrue(countrycode.length() >= 3,
                "Country codes have a minimum length of 3. (Input: " + countrycode + ")");
        List<Language> languages = languageRepository.findByCountrycode(countrycode.toUpperCase());
        Assert.notEmpty(languages,
                "Country language not found for country code = " + countrycode);
        return languages;
    }
    
}
