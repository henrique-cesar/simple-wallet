package io.github.henriquecesar.wallet.persistence.model;

import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.domain.TransactionType;
import io.github.henriquecesar.wallet.persistence.converter.MapConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity(name = "transaction")
@NoArgsConstructor
@Table(name = "transaction", schema = "simple_wallet")
public class TransactionEntity {

    @Id
    private String id;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    private BalanceEntity balance;

    @NotNull
    private BigDecimal value;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    @Convert(converter = MapConverter.class)
    private Map<String, String> complementar;

    public TransactionEntity(Transaction transaction, BalanceEntity balance) {
        if (transaction == null) return;

        this.id = transaction.getId();
        this.balance = balance;
        this.value = transaction.getValue();
        this.description = transaction.getDescription();
        this.createdAt = transaction.getCreatedAt();
        this.updatedAt = transaction.getUpdatedAt();
        this.transactionType = transaction.getType();
        this.complementar = transaction.getDadosComplementares();
    }

    public Transaction toModel() {
        Transaction transaction = new Transaction();
        transaction.setId(this.id);
        transaction.setBalanceId(this.balance.getId());
        transaction.setValue(this.value);
        transaction.setDescription(this.description);
        transaction.setType(this.getTransactionType());
        transaction.setCreatedAt(this.createdAt);
        transaction.setUpdatedAt(this.updatedAt);
        transaction.setDadosComplementares(this.complementar);
        return transaction;
    }

}
