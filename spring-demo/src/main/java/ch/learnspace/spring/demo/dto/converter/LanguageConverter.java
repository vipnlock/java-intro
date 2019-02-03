package ch.learnspace.spring.demo.dto.converter;

import ch.learnspace.spring.demo.data.entity.Language;
import ch.learnspace.spring.demo.dto.entity.LanguageDto;
import org.modelmapper.ModelMapper;

public final class LanguageConverter {

    private LanguageConverter() {
    }

    public static Language convert (LanguageDto languageDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(languageDto, Language.class);
    }

    public static LanguageDto convert (Language language) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(language, LanguageDto.class);
    }
}
