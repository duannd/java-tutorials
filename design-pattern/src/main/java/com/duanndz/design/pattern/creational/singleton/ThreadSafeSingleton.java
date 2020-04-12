package com.duanndz.design.pattern.creational.singleton;

/**
 * Created By ngdduan@gmail.com at 4/13/20 12:32 AM
 */
public final class ThreadSafeSingleton {

    private static volatile ThreadSafeSingleton instance;
    private static final Object mutex = new Object();

    private ThreadSafeSingleton() {

    }

    public static ThreadSafeSingleton getInstance() {
        ThreadSafeSingleton result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new ThreadSafeSingleton();
                }
            }
        }
        return result;
    }

}
