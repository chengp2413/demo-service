package com.example.demo.application.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 平方计算 响应
 */
@Data
@NoArgsConstructor
public class SquareCalculateResponse {

    /**
     * 响应码
     */
    private String respCode;

    /**
     * 响应信息
     */
    private String respMsg;

    /**
     * 计算结果
     */
    private int result;

    /**
     * 交易代码
     */
    private String tranCode;
}
