package ch.learnspace.spring.demo.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class LanguagePK implements Serializable {
    
    @Id
    @Column(name = "countrycode")
    private String countrycode;
    
    @Id
    @Column(name = "language")
    private String language;
    
}
