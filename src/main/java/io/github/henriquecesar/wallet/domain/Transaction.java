package io.github.henriquecesar.wallet.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static io.github.henriquecesar.wallet.domain.TransactionType.*;

@Getter
@Setter
public class Transaction implements Visitable<Transaction> {
    private String id;
    private String balanceId;
    private BigDecimal value;
    private String description;
    private TransactionType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<String, String> dadosComplementares;

    public boolean isCreditOperation() {
        return Set.of(ESTORNO, RECARGA, CANCELAMENTO).contains(type);
    }

    public boolean isDebitOperation() {
        return Set.of(COMPRA, TRANSFERENCIA).contains(type);
    }

    @Override
    public void accept(Visitor<Transaction, ?> visitor) {
        visitor.visit(this);
    }
}
