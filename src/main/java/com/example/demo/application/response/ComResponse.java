package com.example.demo.application.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共响应
 */
@Data
@NoArgsConstructor
public class ComResponse {

    /**
     * 响应码
     */
    private String respCode;

    /**
     * 响应信息
     */
    private String respMsg;
}
