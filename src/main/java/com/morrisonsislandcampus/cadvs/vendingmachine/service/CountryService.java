package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import com.morrisonsislandcampus.cadvs.vendingmachine.entity.Country;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CountryService {

    private static final String COUNTRY_FILE_PATH = "/data/countries.csv";
    private static final String COMMA_DELIMITER = ",";
    private final FileService fileService;

    public CountryService(FileService fiService) {
        fileService = fiService;
    }

    public List<String> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        try {
            List<String> lCountries = this.fileService.readFromFile(COUNTRY_FILE_PATH);
            for (String sCountry : lCountries) {
                String[] aCountry = sCountry.split(COMMA_DELIMITER);
                countries.add(new Country(aCountry[0], aCountry[1], aCountry[2], aCountry[3]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return countries.stream().map(Country::name).toList();
    }
}
