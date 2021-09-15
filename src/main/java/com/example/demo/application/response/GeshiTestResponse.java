package com.example.demo.application.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报文格式测试 响应
 */
@Data
@NoArgsConstructor
public class GeshiTestResponse {

    private ComHeaderResp header;
    private SecBodyResp body;

}
