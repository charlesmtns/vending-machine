package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileService {

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public List<String> readFromFile(String filePath) throws IOException {
        List<String> list = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {
            String sData = this.readFromInputStream(inputStream);
            String[] aData = sData.split(System.lineSeparator());
            list.addAll(Arrays.asList(aData));
        }
        return list;
    }
}
