package com.example.demo.common.exception;

import lombok.Data;

/**
 * 交易服务类异常
 */
@Data
public class TransException extends RuntimeException {

    /**
     * 自定义异常返回码
     */
    private final String errorCode;

    /**
     * 自定义异常返回信息
     */
    private final String errorMessage;

    /**
     * 有参构造参数
     *
     * @param errorCode    返回码
     * @param errorMessage 返回信息
     */
    public TransException(String errorCode, String errorMessage) {
        super(errorMessage, null, false, false);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
