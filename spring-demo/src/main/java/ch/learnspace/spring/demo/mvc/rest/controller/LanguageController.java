package ch.learnspace.spring.demo.mvc.rest.controller;

import ch.learnspace.spring.demo.data.entity.Language;
import ch.learnspace.spring.demo.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/world/language")
public class LanguageController {
    
    private LanguageService languageService;
    
    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }
    
    @GetMapping("/{language}")
    public List<Language> findByLanguage(@PathVariable String language) {
        return languageService.findByLanguage(language);
    }
    
    @GetMapping("/country/{countrycode}")
    public List<Language> findByCountryCode(@PathVariable String countrycode) {
        return languageService.findByCountryCode(countrycode);
    }
}
