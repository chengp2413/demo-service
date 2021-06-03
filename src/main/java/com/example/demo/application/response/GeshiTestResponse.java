package com.example.demo.application.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报文格式测试 响应
 */
@Data
@NoArgsConstructor
public class GeshiTestResponse {

    private Header header;
    private Body body;

    @Data
    public static class Header {
        /**
         * 响应码
         */
        private String respCode;

        /**
         * 响应信息
         */
        private String respMsg;
    }

    @Data
    public static class Body {
        /**
         * 格式
         */
        private String geShi;
    }
}
