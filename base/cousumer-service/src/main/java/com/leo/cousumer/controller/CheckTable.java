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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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
    public List<String> checkMigrate(@RequestParam(name = "checkBvn") String checkBvn) {
        //线程安全
        List<String> resp = new Vector<>();
        log.info("CheckTable-checkMigrate----checkBvn:{}", checkBvn);
        //需要比较的表
        List<BfsSensitiveDataMigrate> allTable = new ArrayList<>();
        for (String table : migratetable.split(",")) {
            List<BfsSensitiveDataMigrate> list = checkOrderMapper.queryMigrateTableList("bfs_sensitive_data_migrate_" + table);
            allTable.addAll(list);
        }
        /*//需要比较的表字段
        Map<String, String> checkTableBvnFieldMap = new HashMap<>();
        log.info("需要比较的表字段:{}", checkTableBvnFieldMap);
        for (String tableAndField : checkTableBvnField.split(",")) {
            checkTableBvnFieldMap.put(tableAndField.split(":")[0], tableAndField.split(":")[1]);
        }*/

        if (CollectionUtils.isNotEmpty(allTable)) {
            log.info("需要比较的表：{}", allTable);

            ExecutorService exec = null;
            try {
                exec = Executors.newFixedThreadPool(8);
                final CountDownLatch latch = new CountDownLatch(allTable.size());
                for (BfsSensitiveDataMigrate bfsSensitiveDataMigrate : allTable) {
                    exec.execute(() -> {
                        try {
                            String tableName = bfsSensitiveDataMigrate.getInstanceName() + "." + bfsSensitiveDataMigrate.getTableName();
                            String tableNameMigrate = bfsSensitiveDataMigrate.getInstanceName() + "_migrate." + bfsSensitiveDataMigrate.getTableName();
                            Long tableNameSum = checkOrderMapper.queryTableSum(tableName);
                            Long tableNameMigrateSum = checkOrderMapper.queryTableSum(tableNameMigrate);
                            if (tableNameSum.longValue() != tableNameMigrateSum.longValue()) {
                                log.error("加密库: {} 数量： {} 与Migrate库: {} 数量： {} 不一致",
                                        tableName, tableNameSum, tableNameMigrate, tableNameMigrateSum);
                                String msg = "加密库: " + tableName + " 数量：" + tableNameSum + " 与Migrate库: " + tableNameMigrate + " 数量： " + tableNameMigrateSum + " 不一致";
                                resp.add(msg);
                            } else {
                                log.info("加密库: {} 数量： {} 与Migrate库: {} 数量： {} 一致",
                                        tableName, tableNameSum, tableNameMigrate, tableNameMigrateSum);
                            }

                            if (!StringUtils.equals(checkBvn, "no")) {
                                /*for (String table : checkTableBvnFieldMap.keySet()) {
                                    if (StringUtils.equals(table, tableName)) {
                                        Long tableNameSumByBvn = checkOrderMapper.queryTableSumByBvn(table, checkTableBvnFieldMap.get(table));
                                        if (tableNameSumByBvn.longValue() != tableNameMigrateSum.longValue()) {
                                            log.error("加密库BVN维度统计: {} 数量： {} 与Migrate库: {} 数量： {} 不一致",
                                                    table, tableNameSumByBvn, tableNameMigrate, tableNameMigrateSum);
                                            String msg = "加密库BVN维度统计: " + table + " 数量：" + tableNameSumByBvn + " 与Migrate库: " + tableNameMigrate + " 数量： " + tableNameMigrateSum + " 不一致";
                                            resp.add(msg);
                                        } else {
                                            log.info("加密库BVN维度统计: {} 数量： {} 与Migrate库: {} 数量： {} 一致",
                                                    table, tableNameSumByBvn, tableNameMigrate, tableNameMigrateSum);
                                        }
                                    }
                                }*/
                                if (bfsSensitiveDataMigrate.getMigrateField().contains("cust_id")) {
                                    log.info("表：{}以cust_id维度统计条数", bfsSensitiveDataMigrate.getTableName());
                                    Long tableNameSumByBvn = checkOrderMapper.queryTableSumByBvn(tableName, "cust_id");
                                    if (tableNameSumByBvn.longValue() != tableNameMigrateSum.longValue()) {
                                        log.error("加密库BVN维度统计: {} 数量： {} 与Migrate库: {} 数量： {} 不一致",
                                                tableName, tableNameSumByBvn, tableNameMigrate, tableNameMigrateSum);
                                        String msg = "加密库BVN维度统计: " + tableName + " 数量：" + tableNameSumByBvn + " 与Migrate库: " + tableNameMigrate + " 数量： " + tableNameMigrateSum + " 不一致";
                                        resp.add(msg);
                                    } else {
                                        log.info("加密库BVN维度统计: {} 数量： {} 与Migrate库: {} 数量： {} 一致",
                                                tableName, tableNameSumByBvn, tableNameMigrate, tableNameMigrateSum);
                                    }
                                }
                                if (bfsSensitiveDataMigrate.getMigrateField().contains("bvn")) {
                                    log.info("表：{}以bvn维度统计条数", bfsSensitiveDataMigrate.getTableName());
                                    Long tableNameSumByBvn = checkOrderMapper.queryTableSumByBvn(tableName, "bvn");
                                    if (tableNameSumByBvn.longValue() != tableNameMigrateSum.longValue()) {
                                        log.error("加密库BVN维度统计: {} 数量： {} 与Migrate库: {} 数量： {} 不一致",
                                                tableName, tableNameSumByBvn, tableNameMigrate, tableNameMigrateSum);
                                        String msg = "加密库BVN维度统计: " + tableName + " 数量：" + tableNameSumByBvn + " 与Migrate库: " + tableNameMigrate + " 数量： " + tableNameMigrateSum + " 不一致";
                                        resp.add(msg);
                                    } else {
                                        log.info("加密库BVN维度统计: {} 数量： {} 与Migrate库: {} 数量： {} 一致",
                                                tableName, tableNameSumByBvn, tableNameMigrate, tableNameMigrateSum);
                                    }
                                }
                                if (bfsSensitiveDataMigrate.getMigrateField().contains("AUTHENTICATION_IDENTITY")) {
                                    log.info("表：{}以AUTHENTICATION_IDENTITY维度统计条数", bfsSensitiveDataMigrate.getTableName());
                                    Long tableNameSumByBvn = checkOrderMapper.queryTableSumByBvn(tableName, "AUTHENTICATION_IDENTITY");
                                    if (tableNameSumByBvn.longValue() != tableNameMigrateSum.longValue()) {
                                        log.error("加密库BVN维度统计: {} 数量： {} 与Migrate库: {} 数量： {} 不一致",
                                                tableName, tableNameSumByBvn, tableNameMigrate, tableNameMigrateSum);
                                        String msg = "加密库BVN维度统计: " + tableName + " 数量：" + tableNameSumByBvn + " 与Migrate库: " + tableNameMigrate + " 数量： " + tableNameMigrateSum + " 不一致";
                                        resp.add(msg);
                                    } else {
                                        log.info("加密库BVN维度统计: {} 数量： {} 与Migrate库: {} 数量： {} 一致",
                                                tableName, tableNameSumByBvn, tableNameMigrate, tableNameMigrateSum);
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
            log.info("CheckTable-checkMigrate完成，对比表数量：{}", allTable.size());
        }

        return resp;
    }

    @GetMapping(value = "/checkBak")
    /* 方法注解 */
    @ApiOperation(value = "checkBak", notes = "")
    public List<String> checkBak(@RequestParam(name = "checkBvn") String checkBvn) {
        //线程安全
        List<String> resp = new Vector<>();
        log.info("CheckTable-checkBak----checkBvn:{}", checkBvn);
        //需要比较的表
        List<BfsSensitiveDataMigrate> allTable = new ArrayList<>();
        for (String table : migratetable.split(",")) {
            List<BfsSensitiveDataMigrate> list = checkOrderMapper.queryMigrateTableList("bfs_sensitive_data_migrate_" + table);
            allTable.addAll(list);
        }
        /*//需要比较的表字段
        Map<String, String> checkTableBvnFieldMap = new HashMap<>();
        for (String tableAndField : checkTableBvnField.split(",")) {
            checkTableBvnFieldMap.put(tableAndField.split(":")[0], tableAndField.split(":")[1]);
        }
        log.info("需要比较的表字段:{}", checkTableBvnFieldMap);*/

        if (CollectionUtils.isNotEmpty(allTable)) {
            log.info("需要比较的表：{}", allTable);
            ExecutorService exec = null;
            try {
                exec = Executors.newFixedThreadPool(8);
                final CountDownLatch latch = new CountDownLatch(allTable.size());
                for (BfsSensitiveDataMigrate bfsSensitiveDataMigrate : allTable) {

                    exec.execute(() -> {
                        try {
                            String tableName = bfsSensitiveDataMigrate.getInstanceName() + "." + bfsSensitiveDataMigrate.getTableName();
                            Long tableNameSum = checkOrderMapper.queryTableSum(tableName);
                            Long tableNameBakSum = checkBakRemoteService.queryTableSum(tableName);
                            if (tableNameSum.longValue() != tableNameBakSum.longValue()) {
                                log.error("加密库: {} 数量： {} 与BAK库: {} 数量： {} 不一致",
                                        tableName, tableNameSum, tableName, tableNameBakSum);
                                String msg = "加密库: " + tableName + " 数量：" + tableNameSum + " 与BAK库: " + tableName + " 数量： " + tableNameBakSum + " 不一致";
                                resp.add(msg);
                            } else {
                                log.info("加密库: {} 数量： {} 与BAK库: {} 数量： {} 一致",
                                        tableName, tableNameSum, tableName, tableNameBakSum);
                            }

                            if (!StringUtils.equals(checkBvn, "no")) {
                                /*for (String table : checkTableBvnFieldMap.keySet()) {
                                    if (StringUtils.equals(table, tableName)) {
                                        Long tableNameSumByBvn = checkOrderMapper.queryTableSumByBvn(table, checkTableBvnFieldMap.get(table));
                                        if (tableNameSumByBvn.longValue() != tableNameBakSum.longValue()) {
                                            log.error("加密库BVN维度统计: {} 数量： {} 与BAK库: {} 数量： {} 不一致",
                                                    table, tableNameSumByBvn, table, tableNameBakSum);
                                            String msg = "加密库BVN维度统计: " + table + " 数量：" + tableNameSumByBvn + " 与BAK库: " + table + " 数量： " + tableNameBakSum + " 不一致";
                                            resp.add(msg);
                                        } else {
                                            log.info("加密库BVN维度统计: {} 数量： {} 与BAK库: {} 数量： {} 一致",
                                                    table, tableNameSumByBvn, table, tableNameBakSum);
                                        }
                                    }
                                }*/
                                if (bfsSensitiveDataMigrate.getMigrateField().contains("cust_id")) {
                                    log.info("表：{}以cust_id维度统计条数", bfsSensitiveDataMigrate.getTableName());
                                    Long tableNameSumByBvn = checkOrderMapper.queryTableSumByBvn(tableName, "cust_id");
                                    if (tableNameSumByBvn.longValue() != tableNameBakSum.longValue()) {
                                        log.error("加密库BVN维度统计: {} 数量： {} 与BAK库: {} 数量： {} 不一致",
                                                tableName, tableNameSumByBvn, tableName, tableNameBakSum);
                                        String msg = "加密库BVN维度统计: " + tableName + " 数量：" + tableNameSumByBvn + " 与BAK库: " + tableName + " 数量： " + tableNameBakSum + " 不一致";
                                        resp.add(msg);
                                    } else {
                                        log.info("加密库BVN维度统计: {} 数量： {} 与BAK库: {} 数量： {} 一致",
                                                tableName, tableNameSumByBvn, tableName, tableNameBakSum);
                                    }
                                }
                                if (bfsSensitiveDataMigrate.getMigrateField().contains("bvn")) {
                                    log.info("表：{}以bvn维度统计条数", bfsSensitiveDataMigrate.getTableName());
                                    Long tableNameSumByBvn = checkOrderMapper.queryTableSumByBvn(tableName, "bvn");
                                    if (tableNameSumByBvn.longValue() != tableNameBakSum.longValue()) {
                                        log.error("加密库BVN维度统计: {} 数量： {} 与BAK库: {} 数量： {} 不一致",
                                                tableName, tableNameSumByBvn, tableName, tableNameBakSum);
                                        String msg = "加密库BVN维度统计: " + tableName + " 数量：" + tableNameSumByBvn + " 与BAK库: " + tableName + " 数量： " + tableNameBakSum + " 不一致";
                                        resp.add(msg);
                                    } else {
                                        log.info("加密库BVN维度统计: {} 数量： {} 与BAK库: {} 数量： {} 一致",
                                                tableName, tableNameSumByBvn, tableName, tableNameBakSum);
                                    }
                                }
                                if (bfsSensitiveDataMigrate.getMigrateField().contains("AUTHENTICATION_IDENTITY")) {
                                    log.info("表：{}以AUTHENTICATION_IDENTITY维度统计条数", bfsSensitiveDataMigrate.getTableName());
                                    Long tableNameSumByBvn = checkOrderMapper.queryTableSumByBvn(tableName, "AUTHENTICATION_IDENTITY");
                                    if (tableNameSumByBvn.longValue() != tableNameBakSum.longValue()) {
                                        log.error("加密库BVN维度统计: {} 数量： {} 与BAK库: {} 数量： {} 不一致",
                                                tableName, tableNameSumByBvn, tableName, tableNameBakSum);
                                        String msg = "加密库BVN维度统计: " + tableName + " 数量：" + tableNameSumByBvn + " 与BAK库: " + tableName + " 数量： " + tableNameBakSum + " 不一致";
                                        resp.add(msg);
                                    } else {
                                        log.info("加密库BVN维度统计: {} 数量： {} 与BAK库: {} 数量： {} 一致",
                                                tableName, tableNameSumByBvn, tableName, tableNameBakSum);
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

            log.info("CheckTable-checkBak完成，对比表数量：{}", allTable.size());
        }
        return resp;
    }

    @GetMapping(value = "/checkRand")
    /* 方法注解 */
    @ApiOperation(value = "checkRand", notes = "")
    public List<String> checkLimit(@RequestParam(name = "limit") String limit) {
        //线程安全
        List<String> resp = new Vector<>();
        log.info("CheckTable-checkRand");
        //需要比较的表
        List<BfsSensitiveDataMigrate> allTable = new ArrayList<>();
        for (String table : migratetable.split(",")) {
            List<BfsSensitiveDataMigrate> list = checkOrderMapper.queryMigrateTableList("bfs_sensitive_data_migrate_" + table);
            allTable.addAll(list);
        }

        if (CollectionUtils.isNotEmpty(allTable)) {
            log.info("需要比较的表：{}", allTable);
            ExecutorService exec = null;
            try {
                exec = Executors.newFixedThreadPool(8);
                final CountDownLatch latch = new CountDownLatch(allTable.size());
                for (BfsSensitiveDataMigrate bfsSensitiveDataMigrate : allTable) {

                    exec.execute(() -> {
                        try {
                            String tableName = bfsSensitiveDataMigrate.getInstanceName() + "." + bfsSensitiveDataMigrate.getTableName();
                            String migrateField = bfsSensitiveDataMigrate.getMigrateField();
                            String migrateFieldType = bfsSensitiveDataMigrate.getMigrateFieldType();
                            String[] migrateFields = migrateField.split(",");
                            String[] migrateFieldTypes = migrateFieldType.split(",");
                            Map<String, String> stringMap = new HashMap<>();
                            Integer i = 0;
                            for (String field : migrateFields) {
                                stringMap.put(field, migrateFieldTypes[i]);
                                i++;
                            }
                            log.info("对比表字段集合：{}", stringMap);
                            if (stringMap.size() > 0) {
                                for (String field : stringMap.keySet()) {
                                    List<String> objects = checkOrderMapper.queryRand(tableName, field, limit);
                                    if (CollectionUtils.isNotEmpty(objects)) {
                                        if (StringUtils.equals(stringMap.get(field), "BVN")) {
                                            Integer size = 0;
                                            for (String value : objects) {
                                                if(StringUtils.isBlank(value)){
                                                    size++;
                                                } else {
                                                    if (value.startsWith("bvn")) {
                                                        size++;
                                                    }
                                                }
                                            }
                                            if (size.intValue() == objects.size()) {
                                                log.info("加密库：{}抽查字段：{}抽查数量：{}加密数量：{}value:{}", tableName, field, objects.size(),
                                                        size.intValue(), objects.stream().collect(Collectors.joining(",")));
                                            } else {
                                                log.error("加密库：{}抽查字段：{}抽查数量：{}加密数量：{}", tableName, field, objects.size(), size.intValue());
                                                String errormsg = "加密库：" + tableName + "抽查字段：" + field + "抽查数量：" + objects.size() + "加密数量：" + size.intValue()
                                                        +"value" + objects.stream().collect(Collectors.joining(","));
                                                resp.add(errormsg);
                                            }
                                        } else if (StringUtils.equals(stringMap.get(field), "PHONE")){
                                            Integer size = 0;
                                            for (String value : objects) {
                                                if(StringUtils.isBlank(value)){
                                                    size++;
                                                } else {
                                                    if (value.startsWith("phone")) {
                                                        size++;
                                                    }
                                                }
                                            }
                                            if (size.intValue() == objects.size()) {
                                                log.info("加密库：{}抽查字段：{}抽查数量：{}加密数量：{}value:{}", tableName, field, objects.size(),
                                                        size.intValue(), objects.stream().collect(Collectors.joining(",")));
                                            } else {
                                                log.error("加密库：{}抽查字段：{}抽查数量：{}加密数量：{}", tableName, field, objects.size(), size.intValue());
                                                String errormsg = "加密库：" + tableName + "抽查字段：" + field + "抽查数量：" + objects.size() + "加密数量：" + size.intValue()
                                                        +"value" + objects.stream().collect(Collectors.joining(","));
                                                resp.add(errormsg);
                                            }
                                        }else if (StringUtils.equals(stringMap.get(field), "BANK_CARD")){
                                            Integer size = 0;
                                            for (String value : objects) {
                                                if(StringUtils.isBlank(value)){
                                                    size++;
                                                } else {
                                                    if (value.startsWith("bankcard")) {
                                                        size++;
                                                    }
                                                }
                                            }
                                            if (size.intValue() == objects.size()) {
                                                log.info("加密库：{}抽查字段：{}抽查数量：{}加密数量：{}value:{}", tableName, field, objects.size(),
                                                        size.intValue(), objects.stream().collect(Collectors.joining(",")));
                                            } else {
                                                log.error("加密库：{}抽查字段：{}抽查数量：{}加密数量：{}", tableName, field, objects.size(), size.intValue());
                                                String errormsg = "加密库：" + tableName + "抽查字段：" + field + "抽查数量：" + objects.size() + "加密数量：" + size.intValue()
                                                        +"value" + objects.stream().collect(Collectors.joining(","));
                                                resp.add(errormsg);
                                            }
                                        }else if (StringUtils.equals(stringMap.get(field), "BANK_ACCT")){
                                            Integer size = 0;
                                            for (String value : objects) {
                                                if(StringUtils.isBlank(value)){
                                                    size++;
                                                } else {
                                                    if (value.startsWith("bankacct")) {
                                                        size++;
                                                    }
                                                }
                                            }
                                            if (size.intValue() == objects.size()) {
                                                log.info("加密库：{}抽查字段：{}抽查数量：{}加密数量：{}value:{}", tableName, field, objects.size(),
                                                        size.intValue(), objects.stream().collect(Collectors.joining(",")));
                                            } else {
                                                log.error("加密库：{}抽查字段：{}抽查数量：{}加密数量：{}", tableName, field, objects.size(), size.intValue());
                                                String errormsg = "加密库：" + tableName + "抽查字段：" + field + "抽查数量：" + objects.size() + "加密数量：" + size.intValue()
                                                        +"value" + objects.stream().collect(Collectors.joining(","));
                                                resp.add(errormsg);
                                            }
                                        }else if (StringUtils.equals(stringMap.get(field), "EMAIL")){
                                            Integer size = 0;
                                            for (String value : objects) {
                                                if(StringUtils.isBlank(value)){
                                                    size++;
                                                } else {
                                                    if (value.startsWith("email")) {
                                                        size++;
                                                    }
                                                }
                                            }
                                            if (size.intValue() == objects.size()) {
                                                log.info("加密库：{}抽查字段：{}抽查数量：{}加密数量：{}value:{}", tableName, field, objects.size(),
                                                        size.intValue(), objects.stream().collect(Collectors.joining(",")));
                                            } else {
                                                log.error("加密库：{}抽查字段：{}抽查数量：{}加密数量：{}", tableName, field, objects.size(), size.intValue());
                                                String errormsg = "加密库：" + tableName + "抽查字段：" + field + "抽查数量：" + objects.size() + "加密数量：" + size.intValue()
                                                        +"value" + objects.stream().collect(Collectors.joining(","));
                                                resp.add(errormsg);
                                            }
                                        }

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

            log.info("CheckTable-checkRand完成，对比表数量：{}", allTable.size());
        }
        return resp;
    }

    public static void main(String[] args) {
        String migrateField =  "cust_id,mobile,topup_mobile,phone,card_no";
        String migrateFieldType = "BVN,PHONE,PHONE,PHONE,BANK_CARD";
        String[] migrateFields = migrateField.split(",");
        String[] migrateFieldTypes = migrateFieldType.split(",");
        Map<String, String> stringMap = new HashMap<>();
        Integer i = 0;
        for (String field : migrateFields) {
            stringMap.put(field, migrateFieldTypes[i]);
            i++;
        }
        System.out.println(stringMap.toString());
    }
}
