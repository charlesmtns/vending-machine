package com.morrisonsislandcampus.cadvs.vendingmachine.entity;

import java.math.BigDecimal;

public class ShoppingCartItem {

    private Product product;
    private int numberItems;
    private BigDecimal quotedPrice;

    public ShoppingCartItem(Product product, int numberItems, BigDecimal quotedPrice) {
        this.product = product;
        this.numberItems = numberItems;
        this.quotedPrice = quotedPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
