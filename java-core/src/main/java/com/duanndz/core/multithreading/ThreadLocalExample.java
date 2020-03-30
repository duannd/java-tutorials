package com.duanndz.core.multithreading;

import java.util.Random;

/**
 * duan.nguyen
 * Datetime 3/30/20 17:12
 */
public class ThreadLocalExample implements Runnable {

    private static final ThreadLocal<String> myName = ThreadLocal.withInitial(() -> "Duan Nguyen");

    public static void main(String[] args) throws InterruptedException {
        ThreadLocalExample obj = new ThreadLocalExample();
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(obj, "" + i);
            Thread.sleep(new Random().nextInt(1000));
            t.start();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread Name= " + Thread.currentThread().getName() + " default Formatter = " + myName.get());
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //formatter pattern is changed here by thread, but it won't reflect to other threads
        myName.set("Nguyen Dinh Duan");

        System.out.println("Thread Name= " + Thread.currentThread().getName() + " formatter = " + myName.get());
    }

}
