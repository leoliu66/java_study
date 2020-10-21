package com.leo.cousumer.study;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName CachedThreadPool
 * @Description 可缓存的线程池
 * @Author liulu_leo
 * @Date 2020/9/29
 * @Version 1.0
 */
public class CachedThreadPool {

    public static void main(String[] args) {
        //核心线程数为0  最大线程数Integer.MAX_VALUE  队列为SynchronousQueue（直接提交队列）
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 50; i++) {
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "处理当前任务");
            });
        }
        pool.shutdown();
    }
}
