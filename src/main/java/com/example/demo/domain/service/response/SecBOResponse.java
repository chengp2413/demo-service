package com.example.demo.domain.service.response;

import lombok.Data;

/**
 * 三方密文请求
 */
@Data
public class SecBOResponse {

    private SecBOHeaderResp header;
    private SecBOBodyResp body;


}
