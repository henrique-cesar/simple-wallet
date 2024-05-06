package io.github.henriquecesar.wallet.core.service;

import io.github.henriquecesar.wallet.domain.Transaction;

public interface FraudeService {

    void verify(Transaction transaction);

}
