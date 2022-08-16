package com.example.demo.danli;

/**
 * @ClassName danli1
 * @Description 单例
 * @Author liulu_leo
 * @Date 2021/2/22
 * @Version 1.0
 */
public class Danli1 {
    private Danli1() {
    }

    private static Danli1 danli1 = null;

    public synchronized static Danli1 getDanli1() {

        if (danli1 == null) {
            danli1 = new Danli1();
        }
        new Thread(){
            @Override
            public void run(){
                System.out.println("1");
            }
        }.start();
        return danli1;
    }

}
