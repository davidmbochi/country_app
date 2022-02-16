package com.quipbank.countryApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "country")
@ToString
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "country_name")
    private String countryName;

    public Country(String countryName) {
        this.countryName = countryName;
    }
}
