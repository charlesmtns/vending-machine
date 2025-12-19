package com.morrisonsislandcampus.cadvs.vendingmachine.entity;

import java.math.BigDecimal;

public record Product(String name, BigDecimal price, int unitOnHand, String imagePath) {

}
