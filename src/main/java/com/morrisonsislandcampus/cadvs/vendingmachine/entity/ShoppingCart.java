package com.morrisonsislandcampus.cadvs.vendingmachine.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<ShoppingCartItem> shoppingCartItems;
    private User user;

    public ShoppingCart() {
        this.shoppingCartItems = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(List<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public BigDecimal totalQuotedPrice() {
        if (this.getShoppingCartItems().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return this.getShoppingCartItems().stream().map(ShoppingCartItem::getQuotedPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
