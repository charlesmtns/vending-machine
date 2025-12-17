package com.morrisonsislandcampus.cadvs.vendingmachine;

import java.math.BigDecimal;

public class Util {

    public static String formatToEuro(BigDecimal price) {
        return "€" + String.format("%.2f", price);
    }
}