package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName Test
 * @Description
 * @Author liulu_leo
 * @Date 2021/3/25
 * @Version 1.0
 */
public class Test1<T> {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread wait = new Thread(new Wait(), "wait");
        wait.start();
        Thread.sleep(1000);
        Thread notify = new Thread(new Notify(), "notify");
        notify.start();
    }

    static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    System.out.println(Thread.currentThread() + " @" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread() + " @完成" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }
    static class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread() + " @" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notify();
                flag = false;
                System.out.println(Thread.currentThread() + " @完成" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }
}
