package com.example.demo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName VirtualAccountPayCallBackReq
 * @Description
 * @Author liulu_leo
 * @Date 2021/6/11
 * @Version 1.0
 */
@Data
public class VirtualAccountPayCallBackReq {

    /**
     * 订单号
     */
    private String tranRef;

    /**
     * 账号
     */
    private String accountNumber;

    private String bvn;

    /**
     * 用户还款金额
     */
    private BigDecimal transactionAmount;

    /**
     * 结算金额
     */
    private BigDecimal settledAmount;

    /**
     * 利息
     */
    private BigDecimal feeAmount;

    private String currency;

    private String channel;

    private String tranDateTime;

}
