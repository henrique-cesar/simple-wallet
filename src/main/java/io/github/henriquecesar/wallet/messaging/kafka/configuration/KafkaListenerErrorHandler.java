package io.github.henriquecesar.wallet.messaging.kafka.configuration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ErrorHandler;

public class KafkaListenerErrorHandler implements ErrorHandler {

    @Override
    public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) {
        // erro cons
    }

}
