package com.duanndz.design.pattern.behavioral.strategy;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    List<Item> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public int calculateTotal() {
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public void pay(PaymentStrategy paymentMethod) {
        int amount = calculateTotal();
        paymentMethod.pay(amount);
    }
}
