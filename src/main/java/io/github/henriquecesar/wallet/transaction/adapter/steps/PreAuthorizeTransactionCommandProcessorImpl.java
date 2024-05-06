package io.github.henriquecesar.wallet.transaction.adapter.steps;

import io.github.henriquecesar.wallet.core.command.CommandContext;
import io.github.henriquecesar.wallet.core.command.TransactionStep;
import io.github.henriquecesar.wallet.core.exception.UnauthorizedException;
import io.github.henriquecesar.wallet.core.persistence.BalancePersistence;
import io.github.henriquecesar.wallet.core.processor.PreAuthorizeTransactionCommandProcessor;
import io.github.henriquecesar.wallet.core.service.FraudeService;
import io.github.henriquecesar.wallet.core.service.TransactionService;
import io.github.henriquecesar.wallet.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.github.henriquecesar.wallet.core.constants.ApplicationConstants.ACCOUNT_ID;

@Component
@RequiredArgsConstructor
public class PreAuthorizeTransactionCommandProcessorImpl implements PreAuthorizeTransactionCommandProcessor {
    private final FraudeService fraudeService;
    private final TransactionService transactionService;
    private final BalancePersistence balancePersistence;

    @Override
    public TransactionStep getStep() {
        return TransactionStep.PRE_AUTHORIZATION;
    }

    @Override
    public Transaction process(CommandContext context) {

        // Iniciando processo de verificação de transação
        Transaction transaction = context.getData(Transaction.class);

        String accountId = context.getProperty(ACCOUNT_ID, String.class);

        if (!balancePersistence.existsByIdAndAccountId(transaction.getBalanceId(), accountId)) {
            throw new UnauthorizedException();
        };

        fraudeService.verify(transaction);
        // Consulta de fraude executada com sucesso

        transactionService.preAuthorize(transaction);
        // Transação pre-autorizada com sucesso

        return transaction;
    }

}
