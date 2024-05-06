package io.github.henriquecesar.wallet.core.command;

import io.github.henriquecesar.wallet.domain.Transaction;

public interface CommandProcessorTransactionStep extends CommandProcessor<Transaction> {

    TransactionStep getStep();

}
