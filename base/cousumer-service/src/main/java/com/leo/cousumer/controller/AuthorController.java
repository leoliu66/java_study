package com.leo.cousumer.controller;

import com.leo.common.dto.ResponseModel;
import com.leo.cousumer.dto.AuthorDTO;
import com.leo.cousumer.entity.Author;
import com.leo.cousumer.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 作者控制器
 *
 * @author LIULU_LEO
 * Date 2020/6/9
 */
@RestController
@RequestMapping("/leo/v1/leo-consumer/authors")
/* 类注解 */
@Api(value = "AuthorController")
@Slf4j
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    @Transactional
    /* 方法注解 */
    @ApiOperation(value = "新增作者", notes = "")
    public ResponseModel insert(@RequestBody AuthorDTO authorDTO){
        log.info("AuthorController.insert事务名称："
                + TransactionSynchronizationManager.getCurrentTransactionName());
        Author user = new Author();
        user.setNickName("aaa");
        user.setRealName("bbb");
        boolean save = authorService.save(user);

        MyThread myThread = new MyThread();
        new Thread(myThread).start();

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for(int i=0; i<100; i++) {
            fixedThreadPool.execute(() -> {
                log.info("liulu");
                log.info(String.valueOf(MDC.getCopyOfContextMap()));
            });
        }
        /*authorService.saveAuthor();*/
        return ResponseModel.ok(user);
    }

    static class MyThread implements Runnable {
        @Override
        public void run() {
            log.info("MyThread");
            Map<String, String> map = MDC.getCopyOfContextMap();
            log.info(String.valueOf(map));
        }
    }

}
