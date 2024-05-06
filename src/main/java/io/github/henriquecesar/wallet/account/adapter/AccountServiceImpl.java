package io.github.henriquecesar.wallet.account.adapter;

import io.github.henriquecesar.wallet.core.exception.TransactionException;
import io.github.henriquecesar.wallet.core.persistence.BalancePersistence;
import io.github.henriquecesar.wallet.core.persistence.TransactionPersistence;
import io.github.henriquecesar.wallet.core.service.AccountService;
import io.github.henriquecesar.wallet.domain.Balance;
import io.github.henriquecesar.wallet.domain.Extract;
import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.domain.TransactionType;
import io.github.henriquecesar.wallet.persistence.model.BalanceEntity;
import io.github.henriquecesar.wallet.persistence.model.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final BalancePersistence balancePersistence;
    private final TransactionPersistence transactionPersistence;

    @Override
    public Balance getBalanceAmount(String accountId, String balanceId) {
        BalanceEntity balanceDB = balancePersistence.findById(balanceId);

        if (balanceDB == null) {
            throw new TransactionException("Balanço não encontrado.");
        }

        return balanceDB.fromModel();
    }

    @Override
    public Extract getExtract(String accountId, String balanceId) {
        Balance balanceAmount = getBalanceAmount(accountId, balanceId);
        List<Transaction> transactions = transactionPersistence.findByBalanceId(balanceId)
                .stream().map(TransactionEntity::toModel).collect(Collectors.toList());

        return new Extract(balanceAmount, transactions);
    }

    @Override
    public Extract getExtract(String accountId, String balanceId, TransactionType transactionType) {
        Balance balanceAmount = getBalanceAmount(accountId, balanceId);
        List<Transaction> transactions = transactionPersistence.findByBalanceIdAndType(balanceId, transactionType)
                .stream().map(TransactionEntity::toModel).collect(Collectors.toList());

        return new Extract(balanceAmount, transactions);
    }

}
