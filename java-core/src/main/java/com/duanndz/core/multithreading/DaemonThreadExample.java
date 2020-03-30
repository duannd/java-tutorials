package com.duanndz.core.multithreading;

/**
 * duan.nguyen
 * Datetime 3/30/20 16:14
 */
public class DaemonThreadExample {

    public static void main(String[] args) throws InterruptedException {
        Thread dt = new Thread(new DaemonThread(), "dt");
        dt.setDaemon(true);
        dt.start();
        //continue program
        Thread.sleep(12000);
        System.out.println("Finishing program");
    }

}
