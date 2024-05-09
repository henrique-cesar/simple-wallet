package io.github.henriquecesar.wallet.core.command.transaction;

import io.github.henriquecesar.wallet.core.command.CommandProcessor;
import io.github.henriquecesar.wallet.domain.Transaction;

public interface CommandProcessorTransactionStep extends CommandProcessor<Transaction> {

    TransactionStep getStep();

}
