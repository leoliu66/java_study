package com.example.snowalkershardingjdbcdemo.service;

import com.example.snowalkershardingjdbcdemo.entity.OrderInfo;
import com.example.snowalkershardingjdbcdemo.mapper.OrderMapper;
import com.example.snowalkershardingjdbcdemo.sjb.repos.tables.*;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.snowalkershardingjdbcdemo.sjb.repos.*;

import java.util.List;

/**
 * @ClassName OrderServiceImpl
 * @Description
 * @Author liulu_leo
 * @Date 2021/11/3
 * @Version 1.0
 */
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private DSLContext dslContext;

    private TOrder tOrder = Tables.T_ORDER;

    @Override
    public List<OrderInfo> queryOrderInfoList(OrderInfo orderInfo) {
        return orderMapper.queryOrderInfoList(orderInfo);
    }

    @Override
    public OrderInfo queryOrderInfoByOrderId(OrderInfo orderInfo) {
        return orderMapper.queryOrderInfoByOrderId(orderInfo);
    }

    @Override
    public int addOrder(OrderInfo orderInfo) {
        return orderMapper.addOrder(orderInfo);
    }

    @Override
    public void deleteOrder(OrderInfo orderInfo) {
        dslContext.delete(tOrder).where(tOrder.ORDER_ID.eq(1L)).execute();
    }
}
