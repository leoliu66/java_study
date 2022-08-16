package com.example.demo.easygongchang;

/**
 * @ClassName NvWa
 * @Description 造人
 * @Author liulu_leo
 * @Date 2021/2/23
 * @Version 1.0
 */
public class NvWa {

    public static void main(String[] args) {
        HumanFactory humanFactory = new HumanFactory();
        Human blackHuman = humanFactory.createHuman(BlackHuman.class);
        blackHuman.getColor();
        blackHuman.talk();
    }
}
