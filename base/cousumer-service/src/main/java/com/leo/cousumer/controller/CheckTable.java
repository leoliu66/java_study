package com.leo.cousumer.controller;

import com.leo.cousumer.entity.BfsSensitiveDataMigrate;
import com.leo.cousumer.mapper.CheckOrderMapper;
import com.leo.product.remoteapi.CheckBakRemoteService;
import com.leo.product.remoteapi.ProductRemoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName CheckMigrate
 * @Description migrate
 * @Author liulu_leo
 * @Date 2020/11/4
 * @Version 1.0
 */
@RestController
@RequestMapping("/leo/v1/check/table")
/* 类注解 */
@Api(value = "AuthorController")
@Slf4j
public class CheckTable {

    @Value("${migratetable}")
    private String migratetable;

    @Value("${checkTableBvnField}")
    private String checkTableBvnField;

    @Autowired
    private CheckOrderMapper checkOrderMapper;

    @Autowired
    private ProductRemoteService checkBakRemoteService;

    @GetMapping(value = "/checkMigrate")
    /* 方法注解 */
    @ApiOperation(value = "checkMigrate", notes = "")
    public List<String> checkMigrate() {
        //线程安全
        List<String> resp = new Vector<>();
        log.info("CheckTable-checkMigrate");
        //需要比较的表
        List<BfsSensitiveDataMigrate> allTable = new ArrayList<>();
        for (String table : migratetable.split(",")) {
            List<BfsSensitiveDataMigrate> list = checkOrderMapper.queryMigrateTableList("bfs_sensitive_data_migrate_" + table);
            allTable.addAll(list);
        }
        //需要比较的表字段
        Map<String, String> checkTableBvnFieldMap = new HashMap<>();
        log.info("需要比较的表字段:{}", checkTableBvnFieldMap);
        for (String tableAndField : checkTableBvnField.split(",")) {
            checkTableBvnFieldMap.put(tableAndField.split(":")[0], tableAndField.split(":")[1]);
        }

        if (CollectionUtils.isNotEmpty(allTable)) {
            log.info("需要比较的表：{}", allTable);

            ExecutorService exec = null;
            try {
                exec = Executors.newFixedThreadPool(10);
                final CountDownLatch latch = new CountDownLatch(allTable.size());
                for (BfsSensitiveDataMigrate bfsSensitiveDataMigrate : allTable) {
                    exec.execute(() -> {
                        try {
                            String tableName = bfsSensitiveDataMigrate.getInstanceName() + "." + bfsSensitiveDataMigrate.getTableName();
                            String tableNameMigrate = bfsSensitiveDataMigrate.getInstanceName() + "_migrate." + bfsSensitiveDataMigrate.getTableName();
                            Long tableNameSum = checkOrderMapper.queryTableSum(tableName);
                            Long tableNameMigrateSum = checkOrderMapper.queryTableSum(tableNameMigrate);
                            if (tableNameSum != tableNameMigrateSum) {
                                log.error("tableName:{}数量：{}与tableNameMigrate:{}数量：{}不一致",
                                        tableName, tableNameSum, tableNameMigrate, tableNameMigrateSum);
                                String msg = "tableName:" + tableName + "数量：" + tableNameSum + "与tableNameMigrate:" + tableNameMigrate + "数量：" + tableNameMigrateSum + "不一致";
                                resp.add(msg);
                            } else {
                                log.info("tableName:{}数量：{}与tableNameMigrate:{}数量：{}一致",
                                        tableName, tableNameSum, tableNameMigrate, tableNameMigrateSum);
                            }
                            for (String table : checkTableBvnFieldMap.keySet()) {
                                if (StringUtils.equals(table, tableName)) {
                                    Long tableNameSumByBvn = checkOrderMapper.queryTableSumByBvn(table, checkTableBvnFieldMap.get(table));
                                    if (tableNameSumByBvn != tableNameMigrateSum) {
                                        log.error("tableNameSumByBvn:{}数量：{}与tableNameMigrate:{}数量：{}不一致",
                                                table, tableNameSumByBvn, tableNameMigrate, tableNameMigrateSum);
                                        String msg = "tableNameSumByBvn:" + table + "数量：" + tableNameSumByBvn + "与tableNameMigrate:" + tableNameMigrate + "数量：" + tableNameMigrateSum + "不一致";
                                        resp.add(msg);
                                    } else {
                                        log.info("tableNameSumByBvn:{}数量：{}与tableNameMigrate:{}数量：{}一致",
                                                table, tableNameSumByBvn, tableNameMigrate, tableNameMigrateSum);
                                    }
                                }
                            }
                        } finally {
                            latch.countDown();
                        }
                    });
                }

                try {
                    latch.await();
                } catch (InterruptedException e) {
                    log.error("对比异常：{}", e);
                }
            } finally {
                // 关闭线程池
                if (exec != null) {
                    exec.shutdown();
                }
            }
        }

        return resp;
    }

    @GetMapping(value = "/checkBak")
    /* 方法注解 */
    @ApiOperation(value = "checkBak", notes = "")
    public List<String> checkBak() {
        //线程安全
        List<String> resp = new Vector<>();
        log.info("CheckTable-checkBak");
        //需要比较的表
        List<BfsSensitiveDataMigrate> allTable = new ArrayList<>();
        for (String table : migratetable.split(",")) {
            List<BfsSensitiveDataMigrate> list = checkOrderMapper.queryMigrateTableList("bfs_sensitive_data_migrate_" + table);
            allTable.addAll(list);
        }
        //需要比较的表字段
        Map<String, String> checkTableBvnFieldMap = new HashMap<>();
        log.info("需要比较的表字段:{}", checkTableBvnFieldMap);
        for (String tableAndField : checkTableBvnField.split(",")) {
            checkTableBvnFieldMap.put(tableAndField.split(":")[0], tableAndField.split(":")[1]);
        }

        if (CollectionUtils.isNotEmpty(allTable)) {
            log.info("需要比较的表：{}", allTable);
            ExecutorService exec = null;
            try {
                exec = Executors.newFixedThreadPool(10);
                final CountDownLatch latch = new CountDownLatch(allTable.size());
                for (BfsSensitiveDataMigrate bfsSensitiveDataMigrate : allTable) {

                    exec.execute(() -> {
                        try {
                            String tableName = bfsSensitiveDataMigrate.getInstanceName() + "." + bfsSensitiveDataMigrate.getTableName();
                            Long tableNameSum = checkOrderMapper.queryTableSum(tableName);
                            Long tableNameBakSum = checkBakRemoteService.queryTableSum(tableName);
                            if (tableNameSum != tableNameBakSum) {
                                log.error("tableName:{}数量：{}与tableNameMigrate:{}数量：{}不一致",
                                        tableName, tableNameSum, "BAK", tableNameBakSum);
                                String msg = "tableName:" + tableName + "数量：" + tableNameSum + "与tableNameMigrate:" + "BAK" + "数量：" + tableNameBakSum + "不一致";
                                resp.add(msg);
                            } else {
                                log.info("tableName:{}数量：{}与tableNameMigrate:{}数量：{}一致",
                                        tableName, tableNameSum, "BAK", tableNameBakSum);
                            }
                            for (String table : checkTableBvnFieldMap.keySet()) {
                                if (StringUtils.equals(table, tableName)) {
                                    Long tableNameSumByBvn = checkOrderMapper.queryTableSumByBvn(table, checkTableBvnFieldMap.get(table));
                                    if (tableNameSumByBvn != tableNameBakSum) {
                                        log.error("tableNameSumByBvn:{}数量：{}与tableNameMigrate:{}数量：{}不一致",
                                                table, tableNameSumByBvn, "BAK", tableNameBakSum);
                                        String msg = "tableNameSumByBvn:" + table + "数量：" + tableNameSumByBvn + "与tableNameMigrate:" + "BAK" + "数量：" + tableNameBakSum + "不一致";
                                        resp.add(msg);
                                    } else {
                                        log.info("tableNameSumByBvn:{}数量：{}与tableNameMigrate:{}数量：{}一致",
                                                table, tableNameSumByBvn, "BAK", tableNameBakSum);
                                    }
                                }
                            }
                        } finally {
                            latch.countDown();
                        }
                    });
                }
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    log.error("对比异常：{}", e);
                }
            } finally {
                // 关闭线程池
                if (exec != null) {
                    exec.shutdown();
                }
            }
        }
        return resp;
    }
}
