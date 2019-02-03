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
import javax.persistence.Table;

@Entity
@Table

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Country {
    
    @Id
    @Column(name = "code")
    private String code;
    
    @Basic
    @Column(name = "name")
    private String name;
    
    @Basic
    @Column(name = "continent")
    private String continent;
    
    @Basic
    @Column(name = "region")
    private String region;
    
    @Basic
    @Column(name = "surfacearea")
    private double surfacearea;
    
    @Basic
    @Column(name = "indepyear")
    private Integer indepyear;
    
    @Basic
    @Column(name = "population")
    private int population;
    
    @Basic
    @Column(name = "lifeexpectancy")
    private Double lifeexpectancy;
    
    @Basic
    @Column(name = "gnp")
    private Double gnp;
    
    @Basic
    @Column(name = "gnpold")
    private Double gnpold;
    
    @Basic
    @Column(name = "localname")
    private String localname;
    
    @Basic
    @Column(name = "governmentform")
    private String governmentform;
    
    @Basic
    @Column(name = "headofstate")
    private String headofstate;
    
    @Basic
    @Column(name = "capital")
    private Integer capital;
    
    @Basic
    @Column(name = "code2")
    private String code2;

//    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
//    @JoinColumn(name = "countrycode")
//    private List<City> cities;
}
