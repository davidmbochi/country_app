package com.quipbank.countryApp;

import com.quipbank.countryApp.model.Country;
import com.quipbank.countryApp.repository.CountryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CountryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryAppApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(CountryRepository countryRepository){
		return args -> {
			List<Country> countries = List.of(
					new Country("Egypt"),
					new Country("Mozambique"),
					new Country("Kenya"),
					new Country("Uganda"),
					new Country("Tanzania"),
					new Country("Congo")
			);

			countryRepository.saveAll(countries);
		};
	}

}
