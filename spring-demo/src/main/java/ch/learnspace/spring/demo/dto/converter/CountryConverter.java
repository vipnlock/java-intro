package ch.learnspace.spring.demo.dto.converter;

import ch.learnspace.spring.demo.data.entity.Country;
import ch.learnspace.spring.demo.dto.entity.CountryDto;
import org.modelmapper.ModelMapper;

public final class CountryConverter {

    private CountryConverter() {
    }

    public static CountryDto convert(Country country) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(country, CountryDto.class);
    }

    public static Country convert(CountryDto countryDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(countryDto, Country.class);
    }
}
