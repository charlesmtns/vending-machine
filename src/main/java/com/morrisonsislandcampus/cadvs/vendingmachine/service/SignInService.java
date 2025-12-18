package com.morrisonsislandcampus.cadvs.vendingmachine.service;

import com.morrisonsislandcampus.cadvs.vendingmachine.entity.User;

import java.util.Optional;

public class SignInService {

    private final UserService userService;

    public SignInService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> signIn(String username, String pin) {
        if (!username.isBlank() && !pin.isBlank()) {
            return this.userService.findByUsernameAndPin(username, pin);
        }
        return Optional.empty();
    }
}
