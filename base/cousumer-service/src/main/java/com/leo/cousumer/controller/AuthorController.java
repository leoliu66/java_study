package com.leo.cousumer.controller;

import com.leo.cousumer.dto.AuthorDTO;
import com.leo.cousumer.entity.Author;
import com.leo.cousumer.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    @Transactional
    /* 方法注解 */
    @ApiOperation(value = "新增作者", notes = "")
    public AuthorDTO insert(@RequestBody AuthorDTO authorDTO) {
        System.out.println("AuthorController.insert事务名称："
                + TransactionSynchronizationManager.getCurrentTransactionName());
        Author user = new Author();
        user.setNickName("aaa");
        user.setRealName("bbb");
        boolean save = authorService.save(user);
        /*authorService.saveAuthor();*/

        return authorDTO;
    }
}
