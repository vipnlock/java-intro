package ch.learnspace.spring.demo.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CityDto implements Serializable {
    private Long id;
    private String name;
    private String countrycode;
    private String district;
    private Integer population;
}
