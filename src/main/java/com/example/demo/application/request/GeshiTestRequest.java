package com.example.demo.application.request;

import com.example.demo.domain.vo.OrgBodyVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 报文格式测试 请求
 *
 * @author chengp
 */

@Data
public class GeshiTestRequest {
    private String appKey;
    private String name;
    private String age;
    private String sign;

    private List<OrgBodyReq> list;
}
