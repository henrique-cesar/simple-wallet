package io.github.henriquecesar.wallet.messaging.kafka.configuration;

import io.github.henriquecesar.wallet.transaction_emitida.TransactionEmitida;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ConsumerRecordFilterStrategy implements RecordFilterStrategy<String, GenericRecord> {

    private static final LinkedList<Class<?>> LIST_CONSUMERS = new LinkedList<>();

    private Boolean transactionConsumerDisabled = false;

    static {
        LIST_CONSUMERS.addAll(List.of(
            TransactionEmitida.class
        ));
    }

    @Override
    public boolean filter(ConsumerRecord<String, GenericRecord> consumerRecord) {
        final var clazz = Optional.ofNullable(consumerRecord)
                .map(ConsumerRecord::value)
                .map(GenericRecord::getClass)
                .orElseThrow(() -> new RuntimeException("Consumer record value is null."));

        return transactionConsumerDisabled && LIST_CONSUMERS.contains(clazz);
    }

    public void setTransactionConsumerDisabled(Boolean transactionConsumerDisabled) {
        if (transactionConsumerDisabled != null) {
            this.transactionConsumerDisabled = transactionConsumerDisabled;
        }
    }
}
