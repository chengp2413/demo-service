package com.example.demo.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Demo测试表
 * DEMO_TEST 实体
 *
 * @author chengp
 * @date 2022-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("DEMO_TEST")
public class DemoTestPO implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.INPUT)
    private String id;

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 状态 N-正常 C-关闭
     */
    @TableField("STATUS")
    private String status;


}
