package com.example.demo.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 批量明细数据
 */
@Data
public class BatchDetailData {

    /**
     * 编号
     */
    private String bh;

    /**
     * 网银流水号
     */
    private String wylsh;

    /**
     * 收款方账号
     */
    private String skfzh;

    /**
     * 收款方户名
     */
    private String skfhm;

    /**
     * 交易金额
     */
    private BigDecimal jyje;

    /**
     * 开户行信息
     */
    private String khhxx;

    /**
     * 交易状态
     */
    private String jyzt;

    /**
     * 备注
     */
    private String bz;

    /**
     * 失败原因
     */
    private String sbyy;
}
