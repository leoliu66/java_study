package com.leo.cousumer.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 消息监听
 *
 * @author LIULU_LEO
 * Date 2020/6/4
 */

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
@Slf4j
public class MsgReceiver {

    @RabbitHandler
    public void process(String message, Channel channel, @Headers Map<String,Object> map){
        log.info("接收处理队列A当中的消息： " + message);

        if(StringUtils.equals(message,"nack")){
            try {
                log.info("消息被 nack 后重新入队列： " + message);
                channel.basicNack((Long)map.get(AmqpHeaders.DELIVERY_TAG),false,false);      //否认消息
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**if(StringUtils.equals(message,"reject")) {
            try {
                log.info("拒绝该消息，消息会被丢弃： " + message);
                channel.basicReject((Long) map.get(AmqpHeaders.DELIVERY_TAG), false);        //拒绝消息
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        try {
            log.info("消息被成功消费" + message);
            channel.basicAck((Long)map.get(AmqpHeaders.DELIVERY_TAG),false);            //确认消息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}