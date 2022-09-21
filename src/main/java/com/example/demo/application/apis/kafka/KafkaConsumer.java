package com.example.demo.application.apis.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kafka消费消息
 * @author chengp
 * @version 1.0
 * @date 2022/9/18 13:35
 */
@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    public void messageHandle(){
        try{
            Map<String, Object> configs = new HashMap<>();
            //设置连接kafka服务器地址
            configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://127.0.0.1:9092");
            //设置key的反序列化类
            configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
            //设置value的反序列化类
            configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            //设置消费组ID
            configs.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer.test");
            //设置偏移量处理
            configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
            //设置消费消息后自动提交
            configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

            //创建消费者对象
            org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(configs);
            List<String> topics = new ArrayList<>();
            topics.add("test_topic");
            consumer.subscribe(topics);
        }catch (Exception e){

        }
    }
}
