package com.leo.cousumer.study;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ScheduledThreadPool
 * @Description 定长线程池，支持定时及周期性任务执行
 * @Author liulu_leo
 * @Date 2020/9/29
 * @Version 1.0
 */
public class ScheduledThreadPool {

    public static void main(String[] args) {
        //定长线程池，支持定时及周期性任务执行  核心线程数为5，最大线程数为Integer.MAX_VALUE，优先级队列DelayedWorkQueue
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        //延时10秒执行
        pool.schedule(() -> {
            System.out.println(Thread.currentThread().getName() + "延时1000毫秒秒处理当前任务");

        }, 1000, TimeUnit.MILLISECONDS);

        //延时10秒周期性执行，周期为1秒
        pool.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + "周期性执行，周期为100毫秒，处理当前任务");

        }, 1000, 100, TimeUnit.MILLISECONDS);

    }

}
