package io.github.henriquecesar.wallet.transaction.adapter.steps;

import io.github.henriquecesar.wallet.core.command.CommandContext;
import io.github.henriquecesar.wallet.core.command.TransactionStep;
import io.github.henriquecesar.wallet.core.processor.ExecuteTransactionCommandProcessor;
import io.github.henriquecesar.wallet.core.service.TransactionService;
import io.github.henriquecesar.wallet.domain.CanalOrigem;
import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.transaction.visitor.DefineDadosComplementaresVisitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.github.henriquecesar.wallet.core.constants.ApplicationConstants.CANAL_ORIGEM;

@Service
@RequiredArgsConstructor
public class ExecuteTransactionCommandProcessorImpl implements ExecuteTransactionCommandProcessor {

    private final TransactionService transactionService;
    private final DefineDadosComplementaresVisitor defineDadosComplementaresVisitor;

    @Override
    public TransactionStep getStep() {
        return TransactionStep.EXECUTE;
    }

    @Override
    public Transaction process(CommandContext context) {
        // Iniciando processo de verificacao de transacao
        Transaction transaction = context.getData(Transaction.class);

        CanalOrigem canalOrigem = context.getProperty(CANAL_ORIGEM, CanalOrigem.class);

        defineDadosComplementaresVisitor.visit(transaction);
        transaction.getDadosComplementares().put(CANAL_ORIGEM, canalOrigem.getValue());

        transactionService.realize(transaction);
        // Balanço sensibilizado com sucesso

        return transaction;
    }

}
