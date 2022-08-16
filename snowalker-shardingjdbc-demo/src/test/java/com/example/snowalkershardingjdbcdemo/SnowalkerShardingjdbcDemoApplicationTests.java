package com.example.snowalkershardingjdbcdemo;

import com.example.snowalkershardingjdbcdemo.entity.OrderInfo;
import com.example.snowalkershardingjdbcdemo.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SnowalkerShardingjdbcDemoApplicationTests {

    @Resource(name = "orderService")
    OrderService orderService;

    //@Test
    public void testInsertOrderInfo() {
        for (int i = 1; i < 1000; i++) {
            long userId = i;
            long orderId = i + 1;
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserName("snowalker");
            orderInfo.setUserId(userId);
            orderInfo.setOrderId(orderId);

            int result = orderService.addOrder(orderInfo);
            if (1 == result) {
                System.out.println("入库成功,orderInfo"+ orderInfo);

            } else {
                System.out.println("入库失敗,orderInfo");
            }
        }
    }

    //@Test
    public void selectOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(200);
        orderInfo.setOrderId(1L);
        orderService.queryOrderInfoList(orderInfo);
    }

    @Test
    public void deleteOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderService.deleteOrder(orderInfo);
    }
}
