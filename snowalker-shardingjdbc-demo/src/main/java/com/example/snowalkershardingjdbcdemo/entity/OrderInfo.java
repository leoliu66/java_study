package com.example.snowalkershardingjdbcdemo.entity;

/**
 * @ClassName OrderInfo
 * @Description
 * @Author liulu_leo
 * @Date 2021/11/3
 * @Version 1.0
 */
public class OrderInfo {

    private Integer id;
    private Long userId;
    private Long orderId;
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
