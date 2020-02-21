package com.duanndz.design.pattern.behavioral.strategy;

public class ShoppingCartTest {

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        var item1 = new Item("1234", 10);
        var item2 = new Item("5678", 40);
        var item3 = new Item("4321", 20);

        cart.addItem(item1);
        cart.addItem(item2);
        cart.addItem(item3);

        //pay by paypal
        cart.pay(new PaypalStrategy("myemail@example.com", "mypwd"));

        //pay by credit card
        cart.pay(new CreditCardStrategy("Pankaj Kumar", "1234567890123456", "786", "12/15"));
    }

}
