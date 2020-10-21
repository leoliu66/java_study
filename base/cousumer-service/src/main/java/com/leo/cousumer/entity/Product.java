package com.leo.cousumer.entity;

import lombok.Data;

/**
 * 产品
 *
 * @author LIULU_LEO
 * Date 2020/5/28
 */
@Data
public class Product {
    private String name = "流露";
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
