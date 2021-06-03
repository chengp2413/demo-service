package com.example.demo.application.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 报文格式测试 请求
 *
 * @author chengp
 */

@Data
public class GeshiTestRequest {

    public Header header;
    public Body body;

    @Data
    public class Header {
        /**
         * 交易代码
         */
        @NotBlank(message = "交易代码不能为空")
        private String tranCode;
    }

    @Data
    public class Body {
        /**
         * 格式
         */
        @NotBlank(message = "格式不能为空")
        private String geShi;
    }
}
