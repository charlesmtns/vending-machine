package com.morrisonsislandcampus.cadvs.vendingmachine.entity;

import java.math.BigDecimal;

public class ShoppingCart {

    private int numberItems;
    private BigDecimal quotedPrice;

    public ShoppingCart() {
        this.quotedPrice = BigDecimal.ZERO;
    }

    public BigDecimal getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(BigDecimal quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public int getNumberItems() {
        return numberItems;
    }

    public void setNumberItems(int numberItems) {
        this.numberItems = numberItems;
    }
}
