package com.example.demo.common.config;

import com.example.demo.application.apis.kafka.KafkaConsumerMessage;
import com.example.demo.application.apis.kafka.KafkaListenThread;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * kafka配置类
 *
 * @author chengp
 * @version 1.0
 * @date 2022/9/18 14:19
 */
@Configuration
@Slf4j
public class KafkaConfiguration {

    /**
     * kafka消费集群（多个,分隔）
     */
    @Value("${kafka.consumer.bootstrap-servers:127.0.0.1:9092}")
    private String consumerBootstrapServers;

    /**
     * key反序列化类
     */
    @Value("${kafka.consumer.key-deserializer:org.apache.kafka.common.serialization.IntegerDeserializer}")
    private String consumerKeyDeserializer;

    /**
     * value反序列化类
     */
    @Value("${kafka.consumer.value-deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
    private String consumerValueDeserializer;

    /**
     * 自动提交
     */
    @Value("${kafka.consumer.auto-commit:true}")
    private String consumerAutoCommit;

    /**
     * 偏移量处理
     */
    @Value("${kafka.consumer.offset:latest}")
    private String consumerOffset;

    /**
     * 消费组ID
     */
    @Value("${kafka.consumer.group-id:test-group-1}")
    private String consumerGroupId;

    /**
     * 消费主题（多个,分隔）
     */
    @Value("${kafka.consumer.topics:test-topic}")
    private String consumerTopics;

    /**
     * 监听消息处理
     */
    @Autowired
    private KafkaConsumerMessage kafkaConsumerMessage;

    /**
     * 消费对象
     */
    public static KafkaConsumer<Integer, String> consumer;


    @Bean
    public void consumerConfig() {
        Map<String, Object> configs = new HashMap<>();
        //设置连接kafka集群
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerBootstrapServers);
        //设置key的反序列化类
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerKeyDeserializer);
        //设置value的反序列化类
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerValueDeserializer);
        //设置消费组ID
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        //设置偏移量处理
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerOffset);
        //设置自动提交
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerAutoCommit);

        //创建消费者对象
        consumer = new KafkaConsumer<Integer, String>(configs);
        consumer.subscribe(Arrays.asList(consumerTopics.split(",")));
        log.info("kafka消息订阅成功!!!");
        log.info("kafka配置: {}", configs);

        //启动线程监听
        KafkaListenThread kafkaListenThread = new KafkaListenThread(kafkaConsumerMessage);
        Thread thread = new Thread(kafkaListenThread);
        thread.start();
    }
}
