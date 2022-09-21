//package com.example.demo.common.config;
//
//import com.example.demo.common.constant.Constants;
//import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * xxljob配置类
// *
// * @author chengp
// * @version 1.0
// * @date 2022/5/17 8:50
// */
//@Configuration
//@Slf4j
//public class XxljobConfiguration {
//
//    /**
//     * 调度中心地址
//     */
//    @Value("${xxljob.executor.addresses}")
//    private String adminAddresses;
//
//    /**
//     * 执行器名字（为空则关闭自动注册）
//     */
//    @Value("${xxljob.executor.appname}")
//    private String appName;
//
//    /**
//     * 执行器端口
//     */
//    @Value("${xxljob.executor.port}")
//    private int port;
//
//    /**
//     * 执行器token
//     */
//    @Value("${xxljob.executor.accesstoken}")
//    private String accessToken;
//
//    /**
//     * 执行器运行日志路径
//     */
//    @Value("${xxljob.executor.logpath}")
//    private String logPath;
//
//    /**
//     * 日志保留天数
//     */
//    @Value("${xxljob.executor.logretentiondays}")
//    private int logRetentionDays;
//
//
//    /**
//     * 参数注入
//     *
//     * @return XxlJobSpringExecutor
//     */
//    @Bean
//    public XxlJobSpringExecutor xxlJobExecutor() {
//        log.info(">>>>>>>>>>>> xxljob config init.");
//        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
//        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
//        xxlJobSpringExecutor.setAppname(appName);
//        xxlJobSpringExecutor.setPort(port);
//        xxlJobSpringExecutor.setAccessToken(accessToken);
//        xxlJobSpringExecutor.setLogPath(logPath);
//        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
//        //指定注册IP
//        String hostAddress = System.getenv(Constants.SVRIP);
//        xxlJobSpringExecutor.setIp(hostAddress);
//
//        log.info(">>>>>>>>>>>> xxljob config adminAddresses: {}", adminAddresses);
//        log.info(">>>>>>>>>>>> xxljob config appName: {}", appName);
//        log.info(">>>>>>>>>>>> xxljob config IP: {}", hostAddress);
//        log.info(">>>>>>>>>>>> xxljob config port: {}", port);
//        log.info(">>>>>>>>>>>> xxljob config accessToken: {}", accessToken);
//        log.info(">>>>>>>>>>>> xxljob config logPath: {}", logPath);
//        log.info(">>>>>>>>>>>> xxljob config logRetentionDays: {}", logRetentionDays);
//        return xxlJobSpringExecutor;
//    }
//}
