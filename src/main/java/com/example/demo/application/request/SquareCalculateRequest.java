package com.example.demo.application.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 平方计算 请求
 *
 * @author chengp
 */
@Data
public class SquareCalculateRequest {

    /**
     * 参数
     */
    private int canShu;

    /**
     * 交易代码
     */
    @NotBlank(message = "交易代码不能为空")
    private String tranCode;
}
