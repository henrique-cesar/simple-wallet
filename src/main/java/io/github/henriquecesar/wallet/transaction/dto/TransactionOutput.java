package io.github.henriquecesar.wallet.transaction.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.domain.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionOutput {

    private String id;
    private String balanceId;
    private BigDecimal value;
    private String description;
    private TransactionType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<String, String> dadosComplementares;

    public TransactionOutput(Transaction transaction) {
        this.id = transaction.getId();
        this.balanceId = transaction.getBalanceId();
        this.value = transaction.getValue();
        this.description = transaction.getDescription();
        this.type = transaction.getType();
        this.createdAt = transaction.getCreatedAt();
        this.updatedAt = transaction.getUpdatedAt();
        this.dadosComplementares = transaction.getDadosComplementares();
    }

}
