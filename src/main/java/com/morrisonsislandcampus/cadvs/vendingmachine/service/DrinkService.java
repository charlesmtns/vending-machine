package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import com.morrisonsislandcampus.cadvs.vendingmachine.entity.Drink;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DrinkService {

    private static final String DRINK_FILE_PATH = "/data/drinks.csv";
    private static final String DELIMITER = ";";
    private final FileService fileService;

    public DrinkService(FileService fileService) {
        this.fileService = fileService;
    }

    public Optional<Drink> find(String name) {
        try {
            List<String> drinks = this.fileService.readFromFile(DRINK_FILE_PATH);
            for (String sDrink : drinks) {
                String[] aDrink = sDrink.split(DELIMITER);
                if (aDrink[0].equals(name)) {
                    return Optional.of(new Drink(aDrink[0], new BigDecimal(aDrink[1]), Integer.parseInt(aDrink[2]), aDrink[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Drink didn't find: " + name);
        }
        return Optional.empty();
    }
}
