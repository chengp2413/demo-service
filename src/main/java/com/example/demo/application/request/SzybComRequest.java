package com.example.demo.application.request;

import lombok.Data;

/**
 * 苏州医保 公共请求
 */
@Data
public class SzybComRequest {

    private String tranCode;

    private String tranDate;


    /**
     * 请求报文截取
     *
     * @param Data 请求报文
     * @return 公共请求
     */
    public SzybComRequest setComRequest(Object Data) {
        SzybComRequest szybComRequest = new SzybComRequest();
        String request = Data.toString();
        String[] datas = request.split("\\|");
        szybComRequest.setTranCode(datas[0].trim());
        szybComRequest.setTranDate(datas[1].trim());
        return szybComRequest;
    }
}
