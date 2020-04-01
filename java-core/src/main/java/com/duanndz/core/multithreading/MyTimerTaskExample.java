package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

/**
 * duan.nguyen
 * Datetime 4/1/20 23:45
 */
public class MyTimerTaskExample {

    private static final Logger log = LoggerFactory.getLogger(MyTimerTaskExample.class);

    public static void main(String[] args) {
        TimerTask timerTask = new MyTimerTask();
        // running timer task as daemon thread
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 5 * 1000, 10 * 1000);
        log.info("TimerTask will start after 5 seconds");
        //cancel after sometime
        try {
            Thread.sleep(120000);
            timer.cancel();
            log.info("TimerTask cancelled");
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
