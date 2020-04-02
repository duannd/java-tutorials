package com.duanndz.core.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * duan.nguyen
 * Datetime 4/2/20 08:32
 */
public class MyCallableExample {

    private static final Logger log = LoggerFactory.getLogger(MyCallableExample.class);

    public static void main(String[] args) {
        //Get ExecutorService from Executors utility class, thread pool size is 10
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //create a list to hold the Future object associated with Callable
        List<Future<String>> list = new ArrayList<>();
        //Create MyCallable instance
        Callable<String> callable = new MyCallable();
        for (int i = 0; i < 100; i++) {
            //submit Callable tasks to be executed by thread pool
            Future<String> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
        }
        for (Future<String> fut : list) {
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                log.info("{} :: {}", LocalDateTime.now(), fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
        executor.shutdown();
    }

}
