package io.github.henriquecesar.wallet.extract.adapter;

import io.github.henriquecesar.wallet.core.persistence.TransactionPersistence;
import io.github.henriquecesar.wallet.core.service.BalanceService;
import io.github.henriquecesar.wallet.core.service.ExtractService;
import io.github.henriquecesar.wallet.domain.Balance;
import io.github.henriquecesar.wallet.domain.Extract;
import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.domain.TransactionType;
import io.github.henriquecesar.wallet.persistence.model.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtractServiceImpl implements ExtractService {

    private final BalanceService balanceService;
    private final TransactionPersistence transactionPersistence;

    private final int PAGE_SIZE = 10;

    @Override
    public Extract getBy(String accountId, String balanceId, int page) {
        Balance balanceAmount = balanceService.getBy(accountId, balanceId);
        Page<Transaction> transactions = transactionPersistence.findByBalanceId(balanceId, PageRequest.of(page, PAGE_SIZE)).map(TransactionEntity::toModel);

        return new Extract(balanceAmount, transactions);
    }

    @Override
    public Extract getBy(String accountId, String balanceId, TransactionType transactionType, int page) {
        Balance balanceAmount = balanceService.getBy(accountId, balanceId);
        Page<Transaction> transactions = transactionPersistence.findByBalanceIdAndType(balanceId, transactionType, PageRequest.of(page, PAGE_SIZE)).map(TransactionEntity::toModel);

        return new Extract(balanceAmount, transactions);
    }

}
