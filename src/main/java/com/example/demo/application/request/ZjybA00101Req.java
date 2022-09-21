package com.example.demo.application.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ZjybA00101Req {

    private ZjybComHead Head;
    private Body Body;

    @Data
    public static class Body {
        private Record record;
    }

    @Data
    public static class Record {
        private String Sid;
    }
}
