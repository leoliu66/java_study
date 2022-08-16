package com.example.snowalkershardingjdbcdemo.service;

import com.example.snowalkershardingjdbcdemo.entity.OrderInfo;

import java.util.List;

public interface OrderService {

    List<OrderInfo> queryOrderInfoList(OrderInfo orderInfo);

    OrderInfo queryOrderInfoByOrderId(OrderInfo orderInfo);

    int addOrder(OrderInfo orderInfo);

    void deleteOrder(OrderInfo orderInfo);
}
