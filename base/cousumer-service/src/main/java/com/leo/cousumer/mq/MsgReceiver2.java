package com.leo.cousumer.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听
 *
 * @author LIULU_LEO
 * Date 2020/6/4
 */

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_B)
@Slf4j
public class MsgReceiver2 {

    @RabbitHandler
    public void process(String content) {
        log.info("接收处理队列B当中的消息： " + content);
    }
}