package com.example.demo.application.response;

import com.example.demo.application.request.ZjybComHead;
import lombok.Data;

@Data
public class ZjybA00101Resp {

    private ZjybComHead Head;
    private Body Body;

    @Data
    public static class Body {
        private String CODE;
        private String TYPE;
        private String MESSAGE;
        private DATA DATA;
    }

    @Data
    public static class DATA {
        private String SM4;
    }
}
