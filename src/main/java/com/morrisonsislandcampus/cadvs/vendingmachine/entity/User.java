package com.morrisonsislandcampus.cadvs.vendingmachine.entity;

import java.math.BigDecimal;

public record User(String name, String username, String pin, BigDecimal balance) {

    public String convertToCsv() {
        return this.name + ";" + this.username + ";" + this.pin + ";" + this.balance;
    }
}
