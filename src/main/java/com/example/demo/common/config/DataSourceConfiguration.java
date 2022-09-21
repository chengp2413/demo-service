package com.example.demo.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourceConfiguration {

    /**
     * 数据库驱动
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * 数据库URL
     */
    @Value("${spring.datasource.url}")
    private String url;

    /**
     * 数据库用户名
     */
    @Value("${spring.datasource.username}")
    private String username;

    /**
     * 数据库密码
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 创建数据源
     *
     * @return DataSource
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.durid")
    public DataSource dataSource() {
        log.info("正在连接数据库...");
        log.info("URL=====>{}", url);
        log.info("USERNAME=====>{}", username);
        log.info("PASSWORD=====>{}", password);
        try (DruidDataSource dataSource = new DruidDataSource()) {
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            log.info("数据库连接成功");
            return dataSource;
        } catch (Exception e) {
            log.error("数据库连接失败：{}", e.getCause().getCause());
            throw e;
        }
    }
}
