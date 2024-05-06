package io.github.henriquecesar.wallet.core.persistence;

import io.github.henriquecesar.wallet.domain.TransactionType;
import io.github.henriquecesar.wallet.persistence.model.TransactionEntity;

import java.util.List;

public interface TransactionPersistence {
    TransactionEntity save(TransactionEntity transaction);
    TransactionEntity findById(String id);
    List<TransactionEntity> findByBalanceId(String balanceId);
    List<TransactionEntity> findByBalanceIdAndType(String balanceId, TransactionType transactionType);
}
