package com.quipbank.countryApp.service;

import com.quipbank.countryApp.model.Country;
import com.quipbank.countryApp.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService{

    private final CountryRepository countryRepository;

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country editCountry(Country country, int id) {
        Country existingCountry = countryRepository.findById(id).get();
        existingCountry.setCountryName(country.getCountryName());
        return countryRepository.save(existingCountry);
    }

    @Override
    @Transactional
    public void deleteCountry(int id) {
        Country country = countryRepository.findById(id).get();
        countryRepository.delete(country);
    }

    @Override
    public List<Country> listCountries() {
        return countryRepository.findAll();
    }
}
