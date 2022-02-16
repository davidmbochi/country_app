package com.quipbank.countryApp.controller;

import com.quipbank.countryApp.model.Country;
import com.quipbank.countryApp.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("country")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;
    @GetMapping("/all")
    public List<Country> findAllCountries(){
        return countryService.listCountries();
    }

    @PostMapping("/add")
    public Country addCountry(@RequestBody Country country){
        return countryService.addCountry(country);
    }

    @PutMapping("/update/{id}")
    public Country updateCountry(@RequestBody Country country,
                                 @PathVariable("id") int id){
        return countryService.editCountry(country,id);
    }

    @GetMapping("/delete/{id}")
    public void deleteCountry(@PathVariable("id") int id){
        countryService.deleteCountry(id);
    }

}
