package ch.learnspace.spring.demo.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CountryDto implements Serializable {
    private String code;
    private String name;
    private String continent;
    private String region;
    private double surfacearea;
    private Integer indepyear;
    private int population;
    private Double lifeexpectancy;
    private Double gnp;
    private Double gnpold;
    private String localname;
    private String governmentform;
    private String headofstate;
    private Integer capital;
    private String code2;
    private List<CityDto> cities;
    private List<LanguageDto> languages;
}
