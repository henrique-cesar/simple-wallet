package io.github.henriquecesar.wallet.core.service;

import io.github.henriquecesar.wallet.domain.Extract;
import io.github.henriquecesar.wallet.domain.TransactionType;

public interface ExtractService {

    Extract getBy(String accountId, String balanceId, int page);

    Extract getBy(String accountId, String balanceId, TransactionType transactionType, int page);

}
