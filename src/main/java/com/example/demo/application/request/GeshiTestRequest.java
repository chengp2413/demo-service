package com.example.demo.application.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 报文格式测试 请求
 *
 * @author chengp
 */

@Data
public class GeshiTestRequest  extends SecRequest{

    private String name;
    private String age;

}
