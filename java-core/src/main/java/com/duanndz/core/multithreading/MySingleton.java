package com.duanndz.core.multithreading;

/**
 * duan.nguyen
 * Datetime 3/30/20 15:42
 */
public class MySingleton {

    private static volatile MySingleton instance;
    private static final Object mutex = new Object();

    private MySingleton() {

    }

//    public static MySingleton getInstance() {
//        if (instance == null) {
//            instance = new MySingleton();
//        }
//        return instance;
//    }


    public static MySingleton getInstance() {
        MySingleton result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new MySingleton();
                }
            }
        }
        return result;
    }
}
