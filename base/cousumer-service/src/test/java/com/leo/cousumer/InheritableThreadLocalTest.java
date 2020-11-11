package com.leo.cousumer;

import org.slf4j.MDC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName InheritableThreadLocalTest
 * @Description
 * @Author liulu_leo
 * @Date 2020/11/10
 * @Version 1.0
 */
public class InheritableThreadLocalTest {

    public static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();

    public static void main(String args[]) {
        threadLocal.set(new Integer(123));

     /*   MyThread myThread = new MyThread();
        new Thread(myThread).start();

        MyThread1 myThread1 = new MyThread1();
        new Thread(myThread1).start();*/

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        for(int i=0; i<100; i++) {
            fixedThreadPool.execute(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("fixedThreadPool = " + threadLocal.get());
            });
        }
        threadLocal.set(new Integer(234));
        System.out.println("main = " + threadLocal.get());
    }

    static class MyThread implements Runnable {
        @Override
        public void run() {
            System.out.println("MyThread = " + threadLocal.get());
        }
    }

    static class MyThread1 implements Runnable {
        @Override
        public void run() {
            System.out.println("MyThread1 = " + threadLocal.get());
        }
    }

}
