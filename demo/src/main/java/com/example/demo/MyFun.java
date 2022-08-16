package com.example.demo;

import org.springframework.beans.BeansException;

/**
 * @ClassName MyFun
 * @Description
 * @Author liulu_leo
 * @Date 2021/3/30
 * @Version 1.0
 */


@FunctionalInterface
public interface MyFun<T> {

    /**
     * Return an instance (possibly shared or independent)
     * of the object managed by this factory.
     *
     * @return the resulting instance
     * @throws BeansException in case of creation errors
     */
    T getObject(String x, String y) throws BeansException;

}

