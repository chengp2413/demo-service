package com.example.demo.domain.service.reqeust;

import lombok.Data;

@Data
public class SecBOHeaderReq {
    private String tranCode;
    private String versionNo;
    private String clientFlowNo;
    private String sysId;
    private String timestamp;
}
