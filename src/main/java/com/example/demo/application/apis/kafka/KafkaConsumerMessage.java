package com.example.demo.application.apis.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

/**
 * kafka消息处理
 *
 * @author chengp
 * @version 1.0
 * @date 2022/9/18 14:48
 */
@Slf4j
@Service
public class KafkaConsumerMessage {

    /**
     * 消息处理
     *
     * @param consumerRecord ConsumerRecord<String, String>
     */
    public void messageHandle(ConsumerRecord<Integer, String> consumerRecord) {
        try {
            log.info("主题: {}", consumerRecord.topic());
            log.info("分区: {}", consumerRecord.partition());
            log.info("偏移量: {}", consumerRecord.offset());
            log.info("Key: {}", consumerRecord.key());
            log.info("Value: {}", consumerRecord.value());
            log.info("=========================================================");
        } catch (Exception e) {
            log.error("kafka消息处理异常 ", e);
        }
    }
}
