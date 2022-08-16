package com.example.demo.abstractgongchang;

/**
 * @ClassName CreatorDengJi1
 * @Description 等级1
 * @Author liulu_leo
 * @Date 2021/2/23
 * @Version 1.0
 */
public class CreatorDengJi1 extends AbstractCreator{

    @Override
    public AbstractProductA getAbstractProductA() {
        return new ProductA1();
    }

    @Override
    public AbstractProductB getAbstractProductB() {
        return new ProductB1();
    }
}
