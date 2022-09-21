package com.example.demo.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 单笔电子回单数据
 */
@Data
public class SingleReceiptData {

    /**
     * 入账日期 yyyyMMdd
     */
    private String rzrq;

    /**
     * 回单编号
     */
    private String hdbh;

    /**
     * 付款方账号
     */
    private String fkfzh;

    /**
     * 付款多级账簿号
     */
    private String fkdjzbh;

    /**
     * 付款方户名
     */
    private String fkfhm;

    /**
     * 付款多级账簿名
     */
    private String fkdjzbm;

    /**
     * 付款方开户行
     */
    private String fkfkhh;

    /**
     * 收款方账号
     */
    private String skfzh;

    /**
     * 收款多级账簿号
     */
    private String skdjzbh;

    /**
     * 收款方户名
     */
    private String skfhm;

    /**
     * 收款多级账簿名
     */
    private String skdjzbm;

    /**
     * 收款方开户行
     */
    private String skfkhh;

    /**
     * 币种
     */
    private String bz;

    /**
     * 交易金额
     */
    private BigDecimal jyje;

    /**
     * 大写金额
     */
    private String dxje;

    /**
     * 交易时间
     */
    private String jysj;

    /**
     * 日志号
     */
    private String rzh;

    /**
     * 状态
     */
    private String zt;

    /**
     * 摘要
     */
    private String zy;

    /**
     * 渠道
     */
    private String qd;

    /**
     * 打印日期
     */
    private String dyrq;

    /**
     * 用户
     */
    private String yh;

    /**
     * 打印行号
     */
    private String dyhh;

    /**
     * 附言
     */
    private String fy;
}
