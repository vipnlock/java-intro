package ch.learnspace.spring.demo.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;


@Entity
@IdClass(LanguagePK.class)

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Language {
    
    @Id
    @Column(name = "countrycode")
    private String countrycode;
    
    @Id
    @Column(name = "language")
    private String language;
    
    @Basic
    @Column(name = "isofficial")
    private boolean isofficial;
    
    @Basic
    @Column(name = "percentage")
    private double percentage;
}
