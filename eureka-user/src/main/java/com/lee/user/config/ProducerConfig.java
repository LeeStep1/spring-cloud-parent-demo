package com.lee.user.config;

import com.lee.user.commonUtil.TransactionListenerImpl;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class ProducerConfig {

    @Autowired
    private TransactionListenerImpl transactionListener;

    @Bean
    public DefaultMQProducer producer() throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("Lee");
        defaultMQProducer.setNamesrvAddr("192.168.150.50:9876;192.168.150.51:9876;192.168.150.54:9876");
        defaultMQProducer.start();
        return defaultMQProducer;
    }

    @Bean
    public TransactionMQProducer TransactionProducer() throws MQClientException {
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer("LeeTransaction");
        transactionMQProducer.setNamesrvAddr("192.168.150.50:9876;192.168.150.51:9876;192.168.150.54:9876");
//        transactionProducer.setExecutorService(executorService);
        transactionMQProducer.setTransactionListener(transactionListener);
        transactionMQProducer.start();
        return transactionMQProducer;
    }

}
