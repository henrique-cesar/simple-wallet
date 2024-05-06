package io.github.henriquecesar.wallet.messaging.kafka.consumer;

import org.apache.avro.generic.GenericRecord;
import org.springframework.core.env.Environment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class EventConsumer {

    private final Environment environment;

    public EventConsumer(Environment environment) {
        this.environment = environment;
    }

    public <T extends GenericRecord> void consume(final T recordValue, final MessageHeaders messageHeaders, final String toggle, final Consumer<T> consumer) {

        if (toggle != null && environment.getProperty(toggle, Boolean.class, Boolean.TRUE)) {
            // Log evento desabilitado
            return;
        }

        try {
            consumer.accept(recordValue);
            // Log evento processado com sucesso
        } catch (Exception exception) {
            // Log erro
        }

    }

}
