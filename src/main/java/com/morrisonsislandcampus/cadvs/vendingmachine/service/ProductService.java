package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import com.morrisonsislandcampus.cadvs.vendingmachine.entity.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private static final String PRODUCT_FILE_PATH = "/data/products.csv";
    private static final String DELIMITER = ";";
    private final FileService fileService;

    public ProductService(FileService fileService) {
        this.fileService = fileService;
    }

    public Optional<Product> find(String name) {
        try {
            List<String> products = this.fileService.readFromFile(PRODUCT_FILE_PATH);
            for (String sProduct : products) {
                String[] aProduct = sProduct.split(DELIMITER);
                if (aProduct[0].equals(name)) {
                    return Optional.of(new Product(aProduct[0], new BigDecimal(aProduct[1]), Integer.parseInt(aProduct[2]), aProduct[3]));
                }
            }
        } catch (IOException e) {
            System.out.println("Product didn't find: " + name);
        }
        return Optional.empty();
    }

    public void updateUnitOnHand(String productName, int unit) {
        try {
            List<String> products = this.fileService.readFromFile(PRODUCT_FILE_PATH);
            List<String> newProducts = new ArrayList<>();
            for (String sProduct : products) {
                String[] aProduct = sProduct.split(DELIMITER);
                if (aProduct[0].equals(productName)) {
                    int unitOnHand = Integer.parseInt(aProduct[2]);
                    aProduct[2] = String.valueOf(unitOnHand - unit);
                }
                String joined = String.join(DELIMITER, aProduct);
                newProducts.add(joined);
            }
            this.fileService.writeToFile(PRODUCT_FILE_PATH, newProducts);
        } catch (IOException e) {
            System.out.println("Product didn't find: " + productName);
        }
    }
}
