package com.example.demo.application.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SecBodyReq {
    private String data;
    private String sign;
}
