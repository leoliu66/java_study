package com.example.demo.yuanxing;

/**
 * @ClassName Test
 * @Description 测试
 * @Author liulu_leo
 * @Date 2021/3/18
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        Thing thing = new Thing();
        thing.setList("liulu");
        Thing cloneThing = thing.clone();
        cloneThing.setList("ll");
        System.out.println(thing.getList());
        System.out.println(cloneThing.getList());
    }
}
