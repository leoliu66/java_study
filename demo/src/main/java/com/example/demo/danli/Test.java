package com.example.demo.danli;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName Test
 * @Description
 * @Author liulu_leo
 * @Date 2021/2/22
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        ExecutorService exec = null;
        exec = Executors.newFixedThreadPool(10);
        for(int i=0; i<10; i++) {
            exec.execute(() -> {
                Danli1 danli1 = Danli1.getDanli1();
                System.out.println(danli1);
            });
        }
        exec.shutdown();
    }

}
