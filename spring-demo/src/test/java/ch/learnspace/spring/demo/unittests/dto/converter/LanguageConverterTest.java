package ch.learnspace.spring.demo.unittests.dto.converter;

import ch.learnspace.spring.demo.data.entity.LanguagePK;
import ch.learnspace.spring.demo.dto.converter.LanguageConverter;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class LanguageConverterTest {
    
    @Test
    void convert_toDto() {
        var language = TestObjectCreator.createRandomLanguage();
        var result = LanguageConverter.convert(language);
        assertEquals(language.getCountrycode(), result.getCountrycode());
        assertEquals(language.getLanguage(), result.getLanguage());
        assertEquals(language.isIsofficial(), result.isIsofficial());
        assertEquals(language.getPercentage(), result.getPercentage());
        assertEquals(language, LanguageConverter.convert(result));
    }
    
    @Test
    void convert_toDbEntity() {
        var languageDto = TestObjectCreator.createRandomLanguageDto();
        var result = LanguageConverter.convert(languageDto);
        assertEquals(languageDto.getCountrycode(), result.getCountrycode());
        assertEquals(languageDto.getLanguage(), result.getLanguage());
        assertEquals(languageDto.isIsofficial(), result.isIsofficial());
        assertEquals(languageDto.getPercentage(), result.getPercentage());
        assertEquals(languageDto, LanguageConverter.convert(result));
    }
    
    @Test
    void createPK() {
        var language = TestObjectCreator.createRandomLanguage();
        var languagePK = new LanguagePK();
        languagePK.setCountrycode(language.getCountrycode());
        languagePK.setLanguage(language.getLanguage());
        assertEquals(language.getLanguage(), languagePK.getLanguage());
        assertEquals(language.getCountrycode(), languagePK.getCountrycode());
        var otherLanguage = TestObjectCreator.createRandomLanguage();
        var otherLanguagePK = new LanguagePK();
        otherLanguagePK.setLanguage(otherLanguage.getLanguage());
        otherLanguagePK.setCountrycode(otherLanguage.getCountrycode());
        assertNotEquals(languagePK, otherLanguagePK);
    }
}