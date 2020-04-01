package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.TimerTask;

/**
 * duan.nguyen
 * Datetime 4/1/20 23:42
 */
public class MyTimerTask extends TimerTask {

    private static final Logger log = LoggerFactory.getLogger(MyTimerTask.class);

    @Override
    public void run() {
        log.info("Timer task started at: {}", LocalDateTime.now());
        completeTask();
        log.info("Timer task finished at: {}", LocalDateTime.now());
    }

    private void completeTask() {
        try {
            //assuming it takes 20 secs to complete the task
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
