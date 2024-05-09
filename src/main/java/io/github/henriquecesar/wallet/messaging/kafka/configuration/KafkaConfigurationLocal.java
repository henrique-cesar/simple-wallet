package io.github.henriquecesar.wallet.messaging.kafka.configuration;

import org.apache.avro.generic.GenericRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@EnableKafka
@Configuration
public class KafkaConfigurationLocal {

    @Value("${kafka.consumer.transaction.enabled}")
    private Boolean featureToggleTransactionConsumer;

    @Bean
    public ProducerFactory<String, GenericRecord> producerFactory(final KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildAdminProperties());
    }

    @Bean
    public KafkaTemplate<String, GenericRecord> kafkaTemplate(final ProducerFactory<String, GenericRecord> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, GenericRecord> consumerFactory(final KafkaProperties kafkaProperties) {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, GenericRecord>> containerFactory(final ConsumerFactory<String, GenericRecord> consumerFactory) {
        ConsumerRecordFilterStrategy consumerRecordFilterStrategy = new ConsumerRecordFilterStrategy();
        consumerRecordFilterStrategy.setTransactionConsumerDisabled(!featureToggleTransactionConsumer);

        final ConcurrentKafkaListenerContainerFactory<String, GenericRecord> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory);
        containerFactory.setConcurrency(3);
        containerFactory.setErrorHandler(new KafkaListenerErrorHandler());
        containerFactory.setAckDiscarded(true);
        containerFactory.setRecordFilterStrategy(consumerRecordFilterStrategy);

        return containerFactory;
    }

}
