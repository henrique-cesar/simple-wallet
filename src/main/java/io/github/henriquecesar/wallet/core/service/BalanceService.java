package io.github.henriquecesar.wallet.core.service;

import io.github.henriquecesar.wallet.domain.Balance;

public interface BalanceService {

    Balance getBy(String accountId, String balanceId);

}
