package com.duanndz.core.multithreading;

import java.util.concurrent.*;

/**
 * duan.nguyen
 * Datetime 4/2/20 01:32
 */
public class WorkerPoolExample {

    public static void main(String[] args) throws InterruptedException {
        //RejectedExecutionHandler implementation
        RejectedExecutionHandlerImpl rejectionHandler = new RejectedExecutionHandlerImpl();

        //Get the ThreadFactory implementation to use
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        //creating the ThreadPoolExecutor
        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(2, 4,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4), threadFactory, rejectionHandler);

        //start the monitoring thread
        MyMonitorThread monitor = new MyMonitorThread(executorPool, 3); // delay 3 seconds

        Thread monitorThread = new Thread(monitor);
        monitorThread.start();

        //submit work to the thread pool
        for (int i = 0; i < 10; i++) {
            executorPool.execute(new WorkerThread("cmd " + (i + 1)));
        }
        Thread.sleep(30000);

        //shut down the pool
        executorPool.shutdown();

        //shut down the monitor thread
        Thread.sleep(5000);
        monitor.shutdown();
    }

}
