package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import com.morrisonsislandcampus.cadvs.vendingmachine.entity.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class SignInService {

    private static final String USER_FILE_PATH = "/data/users.csv";
    private static final String DELIMITER = ";";

    private final FileService fileService;

    public SignInService(FileService fileService) {
        this.fileService = fileService;
    }

    public Optional<User> signIn(String username, String pin) {
        if (!username.isBlank() && !pin.isBlank()) {
            return this.findByUsernameAndPin(username, pin);
        }
        return Optional.empty();
    }

    private Optional<User> findByUsernameAndPin(String username, String pin) {
        try {
            List<String> users = this.fileService.readFromFile(USER_FILE_PATH);
            for (String sUser : users) {
                String[] aUser = sUser.split(DELIMITER);
                if (aUser[1].equals(username) && aUser[2].equals(pin)) {
                    return Optional.of(new User(aUser[0], aUser[1], aUser[2], new BigDecimal(aUser[3])));
                }
            }
        } catch (IOException e) {
            System.out.println("User didn't find: " + username);
        }
        return Optional.empty();
    }
}
