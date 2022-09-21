package com.example.demo.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class GeshiTestVo {
    private String appKey;
    private String name;
    private String age;
    private String sign;

    private List<OrgBodyVO> list;
}
