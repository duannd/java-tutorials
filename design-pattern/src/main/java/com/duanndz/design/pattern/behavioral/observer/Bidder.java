package com.duanndz.design.pattern.behavioral.observer;

import java.math.BigDecimal;

public class Bidder implements Observer {

    private String name;

    public Bidder(String name) {
        this.name = name;
    }

    @Override
    public void update(Observer observer, String productName, BigDecimal bidAmount) {
        if (observer.toString().equals(name)) {
            System.out.println("Hello " + name + "! New bid of amount " + bidAmount + " has been placed on " + productName + " by you");
        } else {
            System.out.println("Hello " + name + "! New bid of amount " + bidAmount + " has been placed on " + productName + " by " + observer);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
