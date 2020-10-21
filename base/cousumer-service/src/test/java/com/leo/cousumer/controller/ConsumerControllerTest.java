package com.leo.cousumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ConsumerControllerTest {
    @Autowired ConsumerController consumerController;

    @Test
    void getConsumer() {

        consumerController.getConsumer();
        System.out.println("测试consumerController成功");
    }
}