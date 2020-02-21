package com.duanndz.design.pattern.behavioral.chain;

import java.util.Scanner;

public class ATMClient {

    public static void main(String[] args) {
        ATMDispenseChain atmDispenser = new ATMDispenseChain();
        while (true) {
            int amount;
            System.out.println("Enter amount to dispense");
            Scanner input = new Scanner(System.in);
            amount = input.nextInt();
            if (amount % 10 != 0) {
                System.out.println("Amount should be in multiple of 10s.");
                return;
            }

            // process the request
            atmDispenser.getChain().dispense(new Currency(amount));
        }
    }

}
