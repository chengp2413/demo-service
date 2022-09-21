package com.example.demo.application.response;

import lombok.Data;

/**
 * 三方密文请求
 */
@Data
public class SecResponse {

    private SecHeaderResp header;
    private SecBodyResp body;


}
