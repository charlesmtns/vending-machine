package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import com.morrisonsislandcampus.cadvs.vendingmachine.entity.County;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountyService {

    private static final String COUNTY_FILE_NAME = "/data/counties.csv";
    private final FileService fileService;

    public CountyService(FileService fileService) {
        this.fileService = fileService;
    }

    public List<String> getAllCounties() {
        List<County> counties = new ArrayList<>();
        try {
            List<String> lCounties = readCountiesFromFile();
            for (String county : lCounties) {
                counties.add(new County(county));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return counties.stream().map(County::name).toList();
    }

    private List<String> readCountiesFromFile() throws IOException {
        List<String> listCounties = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream(COUNTY_FILE_NAME)) {
            String sCounties = fileService.readFromInputStream(inputStream);
            String[] aCounties = sCounties.split(System.lineSeparator());
            listCounties.addAll(Arrays.asList(aCounties));
        }
        return listCounties;
    }
}
