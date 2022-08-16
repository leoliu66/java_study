package com.example.demo.easygongchang;

/**
 * @ClassName BlanckHuman
 * @Description 黑人
 * @Author liulu_leo
 * @Date 2021/2/23
 * @Version 1.0
 */
public class BlackHuman implements Human {
    @Override
    public void getColor() {
        System.out.println("黑皮肤");
    }

    @Override
    public void talk() {
        System.out.println("黑人说话");
    }
}
