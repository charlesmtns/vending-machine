package com.morrisonsislandcampus.cadvs.vendingmachine.entity;

public enum DrinkEnum {

    COKE("Coke"), DIET_COKE("Diet Coke"), FANTA("Fanta"), SPRITE("Sprite");

    private String name;

    DrinkEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
