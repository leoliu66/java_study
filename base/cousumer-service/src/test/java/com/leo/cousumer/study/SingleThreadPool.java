package com.leo.cousumer.study;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName SingleThreadPool
 * @Description 单线程的线程池
 * @Author liulu_leo
 * @Date 2020/9/29
 * @Version 1.0
 */
public class SingleThreadPool {
    public static void main(String[] args) {
        //创建一个单线程的线程池 核心线程数与最大线程数都为1，队列大小为Integer.MAX_VALUE
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 20; i++) {
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "处理当前任务");
            });
        }
        pool.shutdown();
    }

}
