package com.duanndz.concurrency.core;

/**
 * duan.nguyen
 * Datetime 5/20/20 10:11
 */
public class ThreadExample {

    public static void main(String[] args) {

        System.out.println("Async programing with thread starting");
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("Sleep 5s done.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        System.out.println("Async programing with thread end.");
    }
}
