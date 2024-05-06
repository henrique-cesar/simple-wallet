package io.github.henriquecesar.wallet.core.persistence;

import io.github.henriquecesar.wallet.persistence.model.BalanceEntity;

public interface BalancePersistence {
    BalanceEntity findById(String id);
    BalanceEntity findByIdAndAccountId(String id, String accountId);
    boolean existsByIdAndAccountId(String balanceId, String userId);
}
