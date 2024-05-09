package io.github.henriquecesar.wallet.core.service;

import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.domain.TransactionType;

public interface TransactionService {

    void preAuthorize(Transaction transaction);

    void realize(Transaction transaction);


}
