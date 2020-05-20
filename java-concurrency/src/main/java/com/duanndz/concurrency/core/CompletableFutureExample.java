package com.duanndz.concurrency.core;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

/**
 * duan.nguyen
 * Datetime 5/20/20 10:58
 */
@Slf4j
public class CompletableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        NotifySupplier task1 = new NotifySupplier(10000, "One");
        // NotifySupplier task2 = new NotifySupplier(20000, "Two");
        // NotifySupplier task3 = new NotifySupplier(60 * 1000, "Three");

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(task1);
        future1.thenApply((Function<String, Void>) s -> {
            log.info("Apply: {}", s);
            return null;
        });
        String result = future1.get(30, TimeUnit.SECONDS);
        log.info(result);
    }

}
