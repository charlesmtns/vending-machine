package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileService {

    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
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
            if (!sData.isBlank()) {
                String[] aData = sData.split(System.lineSeparator());
                list.addAll(Arrays.asList(aData));
            }
        }
        return list;
    }

    public void writeToFile(String filePath, String text) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Objects.requireNonNull(getClass().getResource(filePath)).getPath()))) {
            bw.write(text);
            System.out.println("Successfully wrote to the file.");
        }
    }

    public void writeToFile(String filePath, List<String> listText) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Objects.requireNonNull(getClass().getResource(filePath)).getPath()))) {
            if (!listText.isEmpty()) {
                for (String s : listText) {
                    bw.write(s);
                    bw.newLine();
                }
            }
            System.out.println("Successfully wrote to the file.");
        }
    }

    public void writeNewLineToFile(String filePath, String text) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Objects.requireNonNull(getClass().getResource(filePath)).getPath(), true))) {
            bw.write(text);
            System.out.println("Successfully wrote to the file.");
        }
    }
}
