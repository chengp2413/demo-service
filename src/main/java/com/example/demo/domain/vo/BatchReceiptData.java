package com.example.demo.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 批量电子回单数据
 */
@Data
public class BatchReceiptData {

    /**
     * 银行批次号
     */
    private String yhpch;

    /**
     * 企业备注/批次信息
     */
    private String pcbz;

    /**
     * 转出账号
     */
    private String zczh;

    /**
     * 转出户名
     */
    private String zchm;

    /**
     * 总笔数
     */
    private String zbs;

    /**
     * 总金额
     */
    private BigDecimal zje;

    /**
     * 交易状态
     */
    private String jyzt;

    /**
     * 预约转账时间
     */
    private String yyzzsj;

    /**
     * 成功笔数
     */
    private String cgbs;

    /**
     * 成功金额
     */
    private BigDecimal cgje;

    /**
     * 失败笔数
     */
    private String sbbs;

    /**
     * 失败金额
     */
    private BigDecimal sbje;

    /**
     * 其他状态笔数
     */
    private String qtztbs;

    /**
     * 其他状态金额
     */
    private BigDecimal qtztje;

    /**
     * 录入员
     */
    private String lry;

    /**
     * 录入时间
     */
    private String lrsj;

    /**
     * 复核员
     */
    private String fhy;

    /**
     * 复核时间
     */
    private String fhsj;

    /**
     * 批量明细数组
     */
    private List<BatchDetailData> batchDetailDataList;
}
