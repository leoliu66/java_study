package com.leo.cousumer.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthorControllerTest {
    @Autowired
    private AuthorController authorController;

    @Test
    void insert(){
       /* System.out.println(authorController.insert());*/
    }
}