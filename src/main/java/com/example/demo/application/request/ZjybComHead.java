package com.example.demo.application.request;

import lombok.Data;

@Data
public class ZjybComHead {
    private String txnDt;
    private String msgRef;
    private String msgId;
    private String txCode;
    private String version;
}
