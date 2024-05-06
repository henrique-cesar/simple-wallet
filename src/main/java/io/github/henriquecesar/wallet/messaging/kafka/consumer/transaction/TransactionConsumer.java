package io.github.henriquecesar.wallet.messaging.kafka.consumer.transaction;

import io.github.henriquecesar.wallet.domain.CanalOrigem;
import io.github.henriquecesar.wallet.core.command.CommandContext;
import io.github.henriquecesar.wallet.core.command.CommandProcessorTransactionHandler;
import io.github.henriquecesar.wallet.messaging.kafka.consumer.EventConsumer;
import io.github.henriquecesar.wallet.transaction_emitida.TransactionEmitida;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(clientIdPrefix = "simple-wallet",
        topics = {"kafka.transaction.topic.name"},
        containerFactory = "containerFactory",
        groupId = "simple-wallet-api"
)
@ConditionalOnProperty(value = "kafka.transaction.consumer.enabled", havingValue = "true")
public class TransactionConsumer {

    private final EventConsumer eventConsumer;
    private final CommandProcessorTransactionHandler<?> commandProcessorHandler;

    @Value("kafka.consumer.trasaction.emitida.enabled")
    private String CONSUMER_TRANSACTION_EMITIDA_ENABLED;

    public TransactionConsumer(EventConsumer eventConsumer, CommandProcessorTransactionHandler<?> commandProcessorHandler) {
        this.eventConsumer = eventConsumer;
        this.commandProcessorHandler = commandProcessorHandler;
    }

    @KafkaHandler
    public void handleTransactionEmitida(TransactionEmitida record, @Headers final MessageHeaders headers) {

        eventConsumer.consume(record, headers, CONSUMER_TRANSACTION_EMITIDA_ENABLED, transactionEmitida -> {

            CommandContext context = new CommandContext();

            try {
                commandProcessorHandler.process(CanalOrigem.KAFKA, context);

            } catch (Exception exception) {
                // Erro ao processar o evento de TransacaoEmitida

            }
        });

    }

}
