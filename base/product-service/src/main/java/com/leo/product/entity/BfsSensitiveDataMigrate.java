package com.leo.product.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName BfsSensitiveDataMigrate
 * @Description
 * @Author liulu_leo
 * @Date 2020/11/4
 * @Version 1.0
 */
@Data
public class BfsSensitiveDataMigrate {

    private Long id;

    private String tableName;

    private String instanceName;

    private String migrateField;

    private String primaryKey;

    private Integer migrateStatus;

    private Date updateTime;

    private String migrateFieldType;
}
