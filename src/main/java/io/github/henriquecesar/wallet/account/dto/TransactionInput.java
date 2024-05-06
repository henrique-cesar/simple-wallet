package io.github.henriquecesar.wallet.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.domain.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionInput {
    private String id;
    private String balanceId;
    private String value;
    private String operation;
    private String description;

    public Transaction toModel() {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setBalanceId(balanceId);
        transaction.setDescription(description);
        transaction.setValue(new BigDecimal(value));
        transaction.setType(TransactionType.entryOf(operation));
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUpdatedAt(LocalDateTime.now());

        return transaction;
    }

}
