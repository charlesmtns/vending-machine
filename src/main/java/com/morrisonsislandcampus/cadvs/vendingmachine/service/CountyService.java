package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import com.morrisonsislandcampus.cadvs.vendingmachine.entity.County;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CountyService {

    private static final String COUNTY_FILE_PATH = "/data/counties.csv";
    private final FileService fileService;

    public CountyService(FileService fileService) {
        this.fileService = fileService;
    }

    public List<String> getAllCounties() {
        List<County> counties = new ArrayList<>();
        try {
            List<String> lCounties = this.fileService.readFromFile(COUNTY_FILE_PATH);
            for (String county : lCounties) {
                counties.add(new County(county));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return counties.stream().map(County::name).toList();
    }
}
