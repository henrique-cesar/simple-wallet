package io.github.henriquecesar.wallet.messaging.kafka.consumer.transaction;

import io.github.henriquecesar.wallet.core.exception.BadRequestException;
import io.github.henriquecesar.wallet.core.exception.BusinessException;
import io.github.henriquecesar.wallet.domain.CanalOrigem;
import io.github.henriquecesar.wallet.core.command.CommandContext;
import io.github.henriquecesar.wallet.core.command.transaction.CommandProcessorTransactionHandler;
import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.domain.TransactionType;
import io.github.henriquecesar.wallet.messaging.kafka.consumer.EventConsumer;
import io.github.henriquecesar.wallet.messaging.kafka.validator.TransactionEmitidaValidator;
import io.github.henriquecesar.wallet.transaction_emitida.TransactionEmitida;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.github.henriquecesar.wallet.core.constants.ApplicationConstants.ACCOUNT_ID;
import static io.github.henriquecesar.wallet.core.constants.ApplicationConstants.CANAL_ORIGEM;

@Component
@RequiredArgsConstructor
@KafkaListener(clientIdPrefix = "simple-wallet",
        topics = {"${kafka.transaction.topic.name}"},
        containerFactory = "containerFactory",
        groupId = "simple-wallet-api"
)
//@ConditionalOnProperty(value = "kafka.transaction.consumer.enabled", havingValue = "true")
public class TransactionConsumer {

    private final EventConsumer eventConsumer;
    private final CommandProcessorTransactionHandler<?> commandProcessorHandler;
    private final TransactionEmitidaValidator transactionEmitidaValidator;

    @Value("kafka.consumer.transaction.emitida.enabled")
    private String CONSUMER_TRANSACTION_EMITIDA_ENABLED;

    @KafkaHandler
    public void handleTransactionEmitida(TransactionEmitida record, @Headers final MessageHeaders headers) {

        eventConsumer.consume(record, headers, CONSUMER_TRANSACTION_EMITIDA_ENABLED, transactionEmitida -> {

            if (transactionEmitida == null) {
                throw new BusinessException("Não foi possível consumir o evento.");
            }

            transactionEmitidaValidator.validate(transactionEmitida).isInvalidThrow(BadRequestException.class);

            Transaction transaction = new Transaction();
            transaction.setId(transactionEmitida.getNumeroIdentificadorTransacao().toString());
            transaction.setBalanceId(transactionEmitida.getNumeroIdentificadorBalanco().toString());
            transaction.setValue(new BigDecimal(transactionEmitida.getValor().toString()));
            transaction.setDescription(transactionEmitida.getDescricao().toString());
            transaction.setType(TransactionType.entryOf(transactionEmitida.getTipoTransacao().toString()));
            transaction.setCreatedAt(LocalDateTime.now());
            transaction.setUpdatedAt(LocalDateTime.now());

            CommandContext context = new CommandContext(transaction);
            context.put(ACCOUNT_ID, transactionEmitida.getNumeroIdentificadorConta().toString());
            context.put(CANAL_ORIGEM, CanalOrigem.KAFKA);

            try {
                commandProcessorHandler.process(CanalOrigem.KAFKA, context);

            } catch (Exception exception) {
                // Erro ao processar o evento de TransacaoEmitida
                throw exception;

            }
        });

    }

}
