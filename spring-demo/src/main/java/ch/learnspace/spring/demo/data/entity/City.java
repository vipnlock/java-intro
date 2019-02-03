package ch.learnspace.spring.demo.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class City {
    @Id
    @Column(name = "id")
    private Long id;
    
    @Basic
    @Column(name = "name")
    private String name;
    
    @Basic
    @Column(name = "countrycode")
    private String countrycode;
    
    @Basic
    @Column(name = "district")
    private String district;
    
    @Basic
    @Column(name = "population")
    private Integer population;
}
