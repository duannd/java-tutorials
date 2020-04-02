package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * duan.nguyen
 * Datetime 4/2/20 13:44
 */
public class FutureTaskExample {

    private static final Logger log = LoggerFactory.getLogger(FutureTaskExample.class);

    public static void main(String[] args) {
        MyCallable callable1 = new MyCallable(2000);
        MyCallable callable2 = new MyCallable(5000);

        FutureTask<String> futureTask1 = new FutureTask<>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<>(callable2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    log.info("Done");
                    //shut down executor service
                    executor.shutdown();
                    return;
                }

                if (!futureTask1.isDone()) {
                    //wait indefinitely for future task to complete
                    log.info("Waiting for future task 1 complete after 2 second");
                    String result1 = futureTask1.get();
                    log.info("FutureTask1 output= {}", result1);
                }

                log.info("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if (s != null) {
                    log.info("FutureTask2 output= {}", s);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //do nothing
            }
        }
    }

}
