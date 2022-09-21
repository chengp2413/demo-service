package com.example.demo.common.util.dzhd;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * HtmlToPdfInterceptor
 */
@Slf4j
public class HtmlToPdfInterceptor extends Thread {
    /**
     * 文件流
     */
    private InputStream inputStream;

    /**
     * HtmlToPdfInterceptor
     *
     * @param inputStream 文件流
     */
    public HtmlToPdfInterceptor(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * run
     */
    public void run() {
        try (InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8")) {
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                log.info("文件转换进度：{}", line);
            }
        } catch (Exception e) {
            log.error("文件转换异常：{}", e.getLocalizedMessage());
        }
    }
}
