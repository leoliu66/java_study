package com.example.demo.abstractgongchang;

/**
 * @ClassName Client
 * @Description 场景类
 * @Author liulu_leo
 * @Date 2021/2/23
 * @Version 1.0
 */
public class Client {

    public static void main(String[] args) {
        AbstractCreator abstractCreator1 = new CreatorDengJi1();
        AbstractCreator abstractCreator2 = new CreatorDengJi2();
        AbstractProductA a1 = abstractCreator1.getAbstractProductA();
        a1.getProductName();
        AbstractProductB b1 = abstractCreator1.getAbstractProductB();
        b1.getProductName();
        AbstractProductA a2 = abstractCreator2.getAbstractProductA();
        a2.getProductName();
        AbstractProductB b2 = abstractCreator2.getAbstractProductB();
        b2.getProductName();
    }

}
