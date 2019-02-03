package ch.learnspace.spring.demo.dto.converter;

import ch.learnspace.spring.demo.data.entity.City;
import ch.learnspace.spring.demo.dto.entity.CityDto;
import org.modelmapper.ModelMapper;

public final class CityConverter {

    private CityConverter() {
    }

    public static CityDto convert(City city) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(city, CityDto.class);
    }

    public static City convert(CityDto cityDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(cityDto, City.class);
    }

}
