package com.example.demo.application.response;

import lombok.Data;

/**
 * @author chengp
 * @version 1.0
 * @date 2022/4/30 11:03
 */
@Data
public class ZjybSendResponse {
    private String sid;
    private String txCode;
    private String orgCode;
    private String digitSign;
    private String jsonData;
    private String md5;
}
