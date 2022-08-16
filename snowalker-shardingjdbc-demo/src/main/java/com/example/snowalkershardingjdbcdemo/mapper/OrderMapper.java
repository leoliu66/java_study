package com.example.snowalkershardingjdbcdemo.mapper;

import com.example.snowalkershardingjdbcdemo.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    // 查询某个用户订单列表
    List<OrderInfo> queryOrderInfoList(OrderInfo orderInfo);

    // 通过订单号查询订单信息
    OrderInfo queryOrderInfoByOrderId(OrderInfo orderInfo);

    // 插入订单信息
    int addOrder(OrderInfo orderInfo);
}
