package com.duanndz.design.pattern.behavioral.observer;

import java.math.BigDecimal;

public class ObserverTest {

    public static void main(String[] args) {
        // Create a subject (product)
        Subject product = new Product("36 inch LED TV", new BigDecimal(350));

        // Create observers
        Observer danny = new Bidder("Duan");
        Observer hang = new Bidder("Hang");
        Observer kh = new Bidder("KH");

        // Register observers
        product.registerObserver(danny);
        product.registerObserver(hang);
        product.registerObserver(kh);

        // Danny will buy with 375$
        product.setBidAmount(danny, new BigDecimal(375));

        // Hang leave out Auction.
        product.removeObserver(hang);

        // KH want to buy with 400$
        product.setBidAmount(kh, new BigDecimal(400));
    }

}
