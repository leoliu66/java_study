package com.leo.cousumer.study;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName FixedThreadPool
 * @Description 固定大小的线程池
 * @Author liulu_leo
 * @Date 2020/9/29
 * @Version 1.0
 */
public class FixedThreadPool {

    // 创建一个固定大小为5的线程池 队列大小为Integer.MAX_VALUE
    private static ExecutorService pool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            pool.submit(()->{
                System.out.println(Thread.currentThread().getName() + "处理当前任务");
            });

        }
        pool.shutdown();

    }

}
