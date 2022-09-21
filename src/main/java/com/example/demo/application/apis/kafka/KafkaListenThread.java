package com.example.demo.application.apis.kafka;

import com.example.demo.common.config.KafkaConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * kafka消息监听线程
 *
 * @author chengp
 * @version 1.0
 * @date 2022/9/18 15:05
 */
@Slf4j
public class KafkaListenThread implements Runnable {

    @Autowired
    private KafkaConsumerMessage kafkaConsumerMessage;

    /**
     * 构造函数
     *
     * @param kafkaConsumerMessage KafkaConsumerMessage
     */
    public KafkaListenThread(KafkaConsumerMessage kafkaConsumerMessage) {
        this.kafkaConsumerMessage = kafkaConsumerMessage;
    }


    @Override
    public void run() {
        log.info("kafka消息监听线程启动...");
        while (true) {
            //拉取主题消息,每3秒拉取一次
            ConsumerRecords<Integer, String> records = KafkaConfiguration.consumer.poll(3000);
            for (ConsumerRecord<Integer, String> record : records) {
                kafkaConsumerMessage.messageHandle(record);
            }
        }
    }
}
