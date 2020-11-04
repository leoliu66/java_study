package com.leo.cousumer.controller;

import com.leo.cousumer.entity.BfsSensitiveDataMigrate;
import com.leo.cousumer.mapper.CheckOrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthorControllerTest {
    @Autowired
    private CheckOrderMapper checkOrderMapper;

    @Test
    void insert(){
        System.out.println(1);
        List<BfsSensitiveDataMigrate> list = checkOrderMapper.queryMigrateTableList("bfs_sensitive_data_migrate_1");
        System.out.println(list);
    }
}