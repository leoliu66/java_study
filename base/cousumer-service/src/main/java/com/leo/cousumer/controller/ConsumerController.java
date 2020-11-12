package com.leo.cousumer.controller;

import com.leo.common.dto.ResponseModel;
import com.leo.cousumer.annotation.LeoLog;
import com.leo.product.dto.ProductDTO;
import com.leo.product.remoteapi.ProductRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消费者控制层
 *
 * @author LIULU_LEO
 * Date 2020/5/28
 */
@RestController
@RequestMapping(value = "/leo/v1/leo-consumer/consumers")
@Slf4j
public class ConsumerController {

    @Autowired
    private ProductRemoteService productRemoteService;

    @GetMapping
    @LeoLog
    public ResponseModel getConsumer(){
        log.info("进入getConsumer方法");
        /*String[] ss = SpringUtil.getApplicationContext().getBeanDefinitionNames();
        for(String s : ss){
            System.out.println(s);
        }*/
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(123L);
        productDTO.setNickName("666");
        String str =  productRemoteService.getProduct(productDTO);
        return ResponseModel.ok((Object) str);

    }

}
