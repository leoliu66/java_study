package com.leo.product.entity;

/**
 * 产品
 *
 * @author LIULU_LEO
 * Date 2020/5/28
 */
public class Product {
    private String name = "liulu";
    private int age;
    private String add;
    private String email;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", add='" + add + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
