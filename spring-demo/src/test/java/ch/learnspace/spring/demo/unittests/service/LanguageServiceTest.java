package ch.learnspace.spring.demo.unittests.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ch.learnspace.spring.demo.data.entity.Language;
import ch.learnspace.spring.demo.data.repositiory.LanguageRepository;
import ch.learnspace.spring.demo.service.LanguageService;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;
import lombok.var;

@ExtendWith(MockitoExtension.class)
class LanguageServiceTest {

    @Mock
    private LanguageRepository languageRepository;
    
    private LanguageService languageService;

    @BeforeEach
    void setup() {
        this.languageService = new LanguageService(languageRepository);
    }

    @Test
    @DisplayName("findByLanguage with valid Input")
    void findByLanguage_validInput() {
        final String language = "not_existing_language";
        final List<Language> referenceList = Stream.generate(TestObjectCreator::createRandomLanguage)
                                                   .limit(10)
                                                   .collect(Collectors.toList());
        // mock repository
        Mockito.when(languageRepository.findByLanguage(language)).thenReturn(referenceList);

        // call controller
        var result = languageService.findByLanguage(language);
        assertEquals(referenceList, result);
    }
    
    @Test
    @DisplayName("findByLanguage with invalid Input")
    void findByLanguage_invalidInput() {
        var language = TestObjectCreator.createRandomLanguage();
        final String languageName = language.getLanguage().toLowerCase();
        var ex = assertThrows(IllegalArgumentException.class,
                () -> languageService.findByLanguage(languageName));

        assertEquals("Country language not found for language = " + languageName, ex.getMessage());
    }
    
    @Test
    @DisplayName("findByCountryCode with valid Input")
    void findByCountryCode_validInput() {
        final String countryCode = "NEC";   // NEC = Not Existing Country
        final List<Language> referenceList = Stream.generate(TestObjectCreator::createRandomLanguage)
                                                   .limit(10)
                                                   .collect(Collectors.toList());

        // mock repository
        Mockito.when(languageRepository.findByCountrycode(countryCode)).thenReturn(referenceList);
        // call controller
        var result = languageService.findByCountryCode(countryCode);
        assertEquals(referenceList, result);
    }
    
    @Test
    @DisplayName("findByCountryCode with invalid Input")
    void findByCountryCode_invalidInput() {
        var country = TestObjectCreator.createRandomCountry();
        final String countryCode = country.getCode();
        var ex = assertThrows(IllegalArgumentException.class,
                () -> languageService.findByCountryCode(countryCode));

        assertEquals("Country language not found for country code = " + countryCode, ex.getMessage());
    }
}