package com.example.demo.domain.service.reqeust;

import com.example.demo.application.request.SecRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GeshiTestBO extends SecRequest {
    @NotBlank(message = "不能为空")
    private String name;
    private String age;
}
