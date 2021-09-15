package com.example.demo.application.request;

import lombok.Data;

/**
 * 三方密文请求
 */
@Data
public class SecRequest {
    private SecHeaderReq header;
    private SecBodyReq body;
}
