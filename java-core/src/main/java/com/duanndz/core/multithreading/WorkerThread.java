package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * duan.nguyen
 * Datetime 4/2/20 01:03
 */
public class WorkerThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(WorkerThread.class);

    private String command;

    public WorkerThread(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        log.info("{} Start. Command = {}", Thread.currentThread().getName(), command);
        processCommand();
        log.info("{} End.", Thread.currentThread().getName());
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.command;
    }
}
