package com.quipbank.countryApp.service;

import com.quipbank.countryApp.model.Country;

import java.util.List;

public interface CountryService {
    Country addCountry(Country country);
    Country editCountry(Country country, int id);
    void deleteCountry(int id);
    List<Country> listCountries();
}
