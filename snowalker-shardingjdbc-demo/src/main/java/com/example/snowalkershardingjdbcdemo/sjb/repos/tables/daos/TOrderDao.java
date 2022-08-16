/*
 * This file is generated by jOOQ.
 */
package com.example.snowalkershardingjdbcdemo.sjb.repos.tables.daos;


import com.example.snowalkershardingjdbcdemo.sjb.repos.tables.TOrder;
import com.example.snowalkershardingjdbcdemo.sjb.repos.tables.records.TOrderRecord;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Repository
public class TOrderDao extends DAOImpl<TOrderRecord, com.example.snowalkershardingjdbcdemo.sjb.repos.tables.pojos.TOrder, Integer> {

    /**
     * Create a new TOrderDao without any configuration
     */
    public TOrderDao() {
        super(TOrder.T_ORDER, com.example.snowalkershardingjdbcdemo.sjb.repos.tables.pojos.TOrder.class);
    }

    /**
     * Create a new TOrderDao with an attached configuration
     */
    @Autowired
    public TOrderDao(Configuration configuration) {
        super(TOrder.T_ORDER, com.example.snowalkershardingjdbcdemo.sjb.repos.tables.pojos.TOrder.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.example.snowalkershardingjdbcdemo.sjb.repos.tables.pojos.TOrder object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.example.snowalkershardingjdbcdemo.sjb.repos.tables.pojos.TOrder> fetchById(Integer... values) {
        return fetch(TOrder.T_ORDER.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.example.snowalkershardingjdbcdemo.sjb.repos.tables.pojos.TOrder fetchOneById(Integer value) {
        return fetchOne(TOrder.T_ORDER.ID, value);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.example.snowalkershardingjdbcdemo.sjb.repos.tables.pojos.TOrder> fetchByUserId(Long... values) {
        return fetch(TOrder.T_ORDER.USER_ID, values);
    }

    /**
     * Fetch records that have <code>order_id IN (values)</code>
     */
    public List<com.example.snowalkershardingjdbcdemo.sjb.repos.tables.pojos.TOrder> fetchByOrderId(Long... values) {
        return fetch(TOrder.T_ORDER.ORDER_ID, values);
    }

    /**
     * Fetch records that have <code>user_name IN (values)</code>
     */
    public List<com.example.snowalkershardingjdbcdemo.sjb.repos.tables.pojos.TOrder> fetchByUserName(String... values) {
        return fetch(TOrder.T_ORDER.USER_NAME, values);
    }


    /**
     * update the not null field in gaven pojo to db, the primary key must not be null
     */
    public int updateByKeyIgnoreNullValue(com.example.snowalkershardingjdbcdemo.sjb.repos.tables.pojos.TOrder value) {
        TOrderRecord record = DSL.using(configuration()).newRecord(TOrder.T_ORDER, value);

        Field[] notNullField = Arrays
            .stream(record.fields())
            .filter(f -> null != record.getValue(f))
            .toArray(Field[]::new);

        return record.update(notNullField);
    }
}