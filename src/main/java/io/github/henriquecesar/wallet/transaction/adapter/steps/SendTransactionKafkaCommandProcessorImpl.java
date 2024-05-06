package io.github.henriquecesar.wallet.transaction.adapter.steps;

import io.github.henriquecesar.wallet.core.command.CommandContext;
import io.github.henriquecesar.wallet.core.command.TransactionStep;
import io.github.henriquecesar.wallet.core.processor.PreAuthorizeTransactionCommandProcessor;
import io.github.henriquecesar.wallet.domain.Transaction;

public class SendTransactionKafkaCommandProcessorImpl implements PreAuthorizeTransactionCommandProcessor {

    @Override
    public TransactionStep getStep() {
        return TransactionStep.SEND_OK_KAFKA;
    }

    @Override
    public Transaction process(CommandContext context) {
        return null;
    }

}
