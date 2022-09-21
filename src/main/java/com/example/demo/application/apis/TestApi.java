package com.example.demo.application.apis;

import com.example.demo.application.request.SquareCalculateRequest;
import com.example.demo.common.util.GetParamUtil;
import com.example.demo.domain.service.TransService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 测试api
 *
 * @author chengp
 */
@Slf4j
@RestController
@Validated
@RequestMapping("/demo")
public class TestApi {

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String str1 = "chengp.pub.testkey";
        String str2 = "chengp.test.testkey";
        String apolloParam1 = GetParamUtil.getApolloParam(str1);
        String apolloParam2 = GetParamUtil.getApolloParam(str2);
        return apolloParam1 + " -- " + apolloParam2;
    }

    /**
     * 注入服务
     */
    @Autowired
    private TransService transService;


    /**
     * 平方计算
     *
     * @param request 请求
     * @return response
     */
    @PostMapping("/squareCalculate")
    public Object squareCalculate(@Valid @RequestBody SquareCalculateRequest request) {
//        log.info("进入TestApi");
//        SquareCalculateResponse response = transService.squareCalculate(request);
//        String key = "apollo.pub.test";
//        Config config = ConfigService.getAppConfig();
//        String value = config.getProperty(key, null);
        String getenv = System.getProperty("apollo.pub.test");
        log.info("进入Api,获取参数：{}", getenv);
        return "success";
    }

    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("进入TestApi");
        String fileName = request.getParameter("filename");
        String fileFullPath = "D:\\chengp\\IDEA_TEST_PATH\\" + request.getParameter("filename");
        log.info("下载文件全路径：{}", fileFullPath);

        File file = new File(fileFullPath);
        //判断文件是否存在
        if (!file.exists()) {
            request.setAttribute("message", "资源不存在，请确认！！！");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }
        //设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        try (FileInputStream fis = new FileInputStream(file);
             ServletOutputStream sos = response.getOutputStream()) {
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) != -1) {
                sos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            log.error("文件下载失败");
        }
    }
}
