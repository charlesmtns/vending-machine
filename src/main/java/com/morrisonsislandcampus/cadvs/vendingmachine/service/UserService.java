package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import com.morrisonsislandcampus.cadvs.vendingmachine.entity.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static final String USER_FILE_PATH = "/data/users.csv";
    private static final String DELIMITER = ";";

    private final FileService fileService;

    public UserService(FileService fileService) {
        this.fileService = fileService;
    }

    public void topUp(String username, BigDecimal amount) {
        Optional<User> optionalUser = this.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            BigDecimal newAmount = user.balance().add(amount);
            User userUpdated = new User(user.name(), user.username(), user.pin(), newAmount);
            try {
                this.fileService.writeToFile(USER_FILE_PATH, userUpdated.convertToCsv());
            } catch (IOException e) {
                throw new RuntimeException("Error top up");
            }
        }
    }

    public Optional<User> findByUsername(String username) {
        try {
            List<String> users = this.fileService.readFromFile(USER_FILE_PATH);
            for (String sUser : users) {
                String[] aUser = sUser.split(DELIMITER);
                if (aUser[1].equals(username)) {
                    return Optional.of(new User(aUser[0], aUser[1], aUser[2], new BigDecimal(aUser[3])));
                }
            }
        } catch (IOException e) {
            System.out.println("User didn't find: " + username);
        }
        return Optional.empty();
    }

    public Optional<User> findByUsernameAndPin(String username, String pin) {
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

    public void save(User user) {
        try {
            this.fileService.writeToFile(USER_FILE_PATH, user.convertToCsv());
        } catch (IOException e) {
            throw new RuntimeException("Error saving user");
        }
    }
}
