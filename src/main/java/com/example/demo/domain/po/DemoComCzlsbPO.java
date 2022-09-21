package com.example.demo.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作流水表
 * DEMO_COM_CZLSB 的实体
 *
 * @author chengp
 * @date 2022-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("DEMO_COM_CZLSB")
public class DemoComCzlsbPO implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 平台日期
     */
    @TableField("PTRQ")
    private String ptrq;

    /**
     * 平台流水 8位日期+8位序列
     */
    @TableId(value = "PTLS", type = IdType.INPUT)
    private String ptls;

    /**
     * 业务种类
     */
    @TableField("YWZL")
    private String ywzl;

    /**
     * 业务编号
     */
    @TableField("YWBH")
    private String ywbh;

    /**
     * 渠道标识
     */
    @TableField("QDBS")
    private String qdbs;

    /**
     * 交易流水
     */
    @TableField("JYLS")
    private String jyls;

    /**
     * 交易代码
     */
    @TableField("JYDM")
    private String jydm;

    /**
     * 交易名称
     */
    @TableField("JYMC")
    private String jymc;

    /**
     * 交易日期
     */
    @TableField("JYRQ")
    private String jyrq;

    /**
     * 交易时间
     */
    @TableField("JYSJ")
    private String jysj;

    /**
     * 服务IP
     */
    @TableField("FWIP")
    private String fwip;

    /**
     * 交易状态 00-初始 01-成功 02-失败 03-处理中
     */
    @TableField("JYZT")
    private String jyzt;

    /**
     * 状态说明
     */
    @TableField("ZTSM")
    private String ztsm;

    /**
     * 创建时间
     */
    @TableField("CJSJ")
    private String cjsj;

    /**
     * 更新时间
     */
    @TableField("GXSJ")
    private String gxsj;


}
