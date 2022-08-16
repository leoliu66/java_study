package com.example.demo.easygongchang;

/**
 * @ClassName YellowHuman
 * @Description 黄种人
 * @Author liulu_leo
 * @Date 2021/2/23
 * @Version 1.0
 */
public class YellowHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("黄皮肤");
    }

    @Override
    public void talk() {
        System.out.println("黄种人说话");
    }
}
