package com.example.demo.application.request;

import lombok.Data;

@Data
public class ZjybSendRequest {
    private String sid;
    private String txCode;
    private String orgCode;
    private String digitSign;
    private String jsonData;
    private String md5;
}
