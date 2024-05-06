package io.github.henriquecesar.wallet.core.service;

import io.github.henriquecesar.wallet.domain.Balance;
import io.github.henriquecesar.wallet.domain.Extract;
import io.github.henriquecesar.wallet.domain.TransactionType;

public interface AccountService {

    Balance getBalanceAmount(String accountId, String balanceId);

    Extract getExtract(String accountId, String balanceId);

    Extract getExtract(String accountId, String balanceId, TransactionType transactionType);

}
