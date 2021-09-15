package com.example.demo.application.request;

import lombok.Data;

@Data
public class SecHeaderReq {
    private String tranCode;
    private String versionNo;
    private String clientFlowNo;
    private String sysId;
    private String timestamp;
}
