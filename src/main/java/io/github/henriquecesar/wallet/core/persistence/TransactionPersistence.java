package io.github.henriquecesar.wallet.core.persistence;

import io.github.henriquecesar.wallet.domain.TransactionType;
import io.github.henriquecesar.wallet.persistence.model.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionPersistence {
    TransactionEntity save(TransactionEntity transaction);
    TransactionEntity findById(String id);
    Page<TransactionEntity> findByBalanceId(String balanceId, Pageable pageable);
    Page<TransactionEntity> findByBalanceIdAndType(String balanceId, TransactionType transactionType, Pageable pageable);
}
