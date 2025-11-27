package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import com.morrisonsislandcampus.cadvs.vendingmachine.entity.Country;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CountryService {

    private static final String COUNTRY_FILE_NAME = "/data/countries.csv";
    private static final String COMMA_DELIMITER = ",";
    private final FileService fileService;

    public CountryService(FileService fiService) {
        fileService = fiService;
    }

    public List<String> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        try {
            List<String[]> lCountries = readCountriesFromFile();
            for (String[] aCountry : lCountries) {
                countries.add(new Country(aCountry[0], aCountry[1], aCountry[2], aCountry[3]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return countries.stream().map(Country::name).toList();
    }

    private List<String[]> readCountriesFromFile() throws IOException {
        List<String[]> listCountries = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream(COUNTRY_FILE_NAME)) {
            String sCountries = fileService.readFromInputStream(inputStream);
            String[] split = sCountries.split(System.lineSeparator());
            for (String countryLine : split) {
                String[] country = countryLine.split(COMMA_DELIMITER);
                listCountries.add(country);
            }
        }
        return listCountries;
    }
}
