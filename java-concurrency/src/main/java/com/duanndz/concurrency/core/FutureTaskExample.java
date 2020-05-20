package com.duanndz.concurrency.core;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * duan.nguyen
 * Datetime 5/20/20 10:26
 */
@Slf4j
public class FutureTaskExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        int timeout = 30 * 1000; // 30 seconds.

        CallApiTask task1 = new CallApiTask(); // 1 seconds
        CallApiTask task2 = new CallApiTask(10 * 1000); // 10 seconds
        CallApiTask task3 = new CallApiTask(60 * 1000); // 60 seconds

        Future<Long> future1 = threadPool.submit(task1);
        Future<Long> future2 = threadPool.submit(task2);
        Future<Long> future3 = threadPool.submit(task3);

        Long result1 = future1.get(timeout, TimeUnit.MILLISECONDS);
        log.info("Result 1: {}", result1);

        Long result2 = future2.get(timeout, TimeUnit.MILLISECONDS);
        log.info("Result 2: {}", result2);

        try {
            Long result3 = future3.get(timeout, TimeUnit.MILLISECONDS); // TimeoutException
            log.info("Result 3: {}", result3);
        } catch (InterruptedException | TimeoutException ex) {
            log.warn(ex.getMessage(), ex);
        }

        threadPool.shutdown();
    }

}
