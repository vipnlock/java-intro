package ch.learnspace.spring.demo.integration.mvc.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.learnspace.spring.demo.data.entity.Language;
import ch.learnspace.spring.demo.data.repositiory.LanguageRepository;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;
import lombok.var;

@SpringBootTest
@AutoConfigureMockMvc
class LanguageControllerTest {
    
    @Autowired
    private LanguageRepository languageRepository;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    @DisplayName("findByLanguage with valid input")
    void findByLanguage_validInput() throws Exception {
        final String language = "not_existing_language";
        final List<Language> referenceList = Stream.generate(TestObjectCreator::createRandomLanguage)
                                                   .limit(10)
                                                   .peek(lang -> lang.setLanguage(language))
                                                   .collect(Collectors.toList());
        final Iterable<Language> savedLanguages = languageRepository.saveAll(referenceList);

        final String json = mockMvc.perform(MockMvcRequestBuilders.get("/api/world/language/" + language))
                                   .andExpect(MockMvcResultMatchers.status().isOk())
                                   .andReturn().getResponse().getContentAsString();

        final Language[] foundLanguages = new ObjectMapper().readValue(json, Language[].class);
        assertNotNull(foundLanguages);
        assertThat(foundLanguages).hasSize(10);
        assertThat(foundLanguages).containsAll(savedLanguages);
    }

    @Test
    @DisplayName("findByLanguage with invalid input")
    void findByLanguage_invalidInput() throws Exception {
        final String language = "secret_language";

        String exceptionMessage = mockMvc.perform(MockMvcRequestBuilders.get("/api/world/language/" + language))
                                         .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                         .andReturn().getResponse().getContentAsString();
        assertEquals("Country language not found for language = " + language, exceptionMessage);
    }
    
    @Test
    @DisplayName("findByCountryCode with valid input")
    void findByCountryCode_validInput() throws Exception {
        final String countryCode = "NEC";   // NEC = Not Existing Country
        final List<Language> referenceList = Stream.generate(TestObjectCreator::createRandomLanguage)
                                                   .limit(10)
                                                   .peek(lang -> lang.setCountrycode(countryCode))
                                                   .collect(Collectors.toList());
        final Iterable<Language> savedLanguages = languageRepository.saveAll(referenceList);

        final String json = mockMvc.perform(MockMvcRequestBuilders.get("/api/world/language/country/" + countryCode))
                                   .andExpect(MockMvcResultMatchers.status().isOk())
                                   .andReturn().getResponse().getContentAsString();

        final Language[] foundLanguages = new ObjectMapper().readValue(json, Language[].class);
        assertNotNull(foundLanguages);
        assertThat(foundLanguages).hasSize(10);
        assertThat(foundLanguages).containsAll(savedLanguages);
    }
    
    @Test
    @DisplayName("findByCountrycode with invalid input")
    void findByCountryCode_invalidInput() throws Exception {
        var country = TestObjectCreator.createRandomCountry();
        final String countryCode = country.getCode();

        String exceptionMessage = mockMvc.perform(MockMvcRequestBuilders.get("/api/world/language/country/" + countryCode))
                                         .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                         .andReturn().getResponse().getContentAsString();

        assertEquals("Country language not found for country code = " + countryCode, exceptionMessage);
    }

}