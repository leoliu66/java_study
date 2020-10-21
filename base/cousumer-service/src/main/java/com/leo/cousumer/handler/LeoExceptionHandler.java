package com.leo.cousumer.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常拦截器
 *
 * @author LIULU_LEO
 * Date 2020/6/2
 */
@ControllerAdvice
public class LeoExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public void handerLeoException(){

    }
}
