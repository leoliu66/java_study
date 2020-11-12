package com.leo.cousumer.controller;

import com.leo.common.dto.ResponseModel;
import com.leo.common.utils.SpringUtil;
import com.leo.cousumer.dto.AuthorDTO;
import com.leo.cousumer.entity.Author;
import com.leo.cousumer.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    BeanFactory beanFactory;

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
        SpringUtil.getBean(AuthorController.class).asyncMethod();
        /*authorService.saveAuthor();*/
        return ResponseModel.ok(user);
    }

    @Async("newTask")
    @Scheduled
    public void asyncMethod() {
        log.info("Start Async Method");
        log.info(String.valueOf(MDC.getCopyOfContextMap()));
        try {

            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("End Async Method");
    }
}
