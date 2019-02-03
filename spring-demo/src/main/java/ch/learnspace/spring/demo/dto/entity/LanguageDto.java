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
public class LanguageDto implements Serializable {
    private String countrycode;
    private String language;
    private boolean isofficial;
    private double percentage;
}
