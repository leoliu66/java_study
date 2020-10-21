package com.leo.cousumer.mq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MsgProducerTest {

    @Autowired
    private MsgProducer msgProducer;

    @Test
    void sendMsg() {
        /*for (int i = 0; i < 100; i++) {

         *//*try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*//*
                msgProducer.sendMsg(String.valueOf(i));

        }*/
        /*Integer i = 0;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            msgProducer.sendMsg(String.valueOf(i));

        }
*/
        msgProducer.sendMsg("nack");
        msgProducer.sendMsg("reject");
        msgProducer.sendMsg("ok");

    }
}