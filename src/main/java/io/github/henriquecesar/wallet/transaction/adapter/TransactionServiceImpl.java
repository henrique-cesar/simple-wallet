package io.github.henriquecesar.wallet.transaction.adapter;

import io.github.henriquecesar.wallet.core.exception.TransactionException;
import io.github.henriquecesar.wallet.core.persistence.BalancePersistence;
import io.github.henriquecesar.wallet.core.persistence.TransactionPersistence;
import io.github.henriquecesar.wallet.core.service.TransactionService;
import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.domain.TransactionType;
import io.github.henriquecesar.wallet.persistence.model.BalanceEntity;
import io.github.henriquecesar.wallet.persistence.model.TransactionEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionServiceImpl implements TransactionService {

    public TransactionServiceImpl(BalancePersistence balancePersistence, TransactionPersistence transactionPersistence) {
        this.balancePersistence = balancePersistence;
        this.transactionPersistence = transactionPersistence;
    }

    private final BalancePersistence balancePersistence;
    private final TransactionPersistence transactionPersistence;

    @Override
    public void preAuthorize(Transaction transaction) {
        BalanceEntity balance = balancePersistence.findById(transaction.getBalanceId());

        if (balance.isLocked()) {
            throw new TransactionException("Balanço possui restrições.");
        }

        if (transaction.isDebitOperation()) {
            BigDecimal amountBalance = balance.getAmount();
            BigDecimal newBalance = amountBalance.subtract(transaction.getValue());

            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new TransactionException("Saldo insuficiente.");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void realize(Transaction transaction) {
        if (transactionPersistence.findById(transaction.getId()) != null) {
            throw new TransactionException("Essa transação já existe.");
        }

        if (!transaction.isCreditOperation() && !transaction.isDebitOperation())
            throw new TransactionException("Operação não permitida.");

        if (transaction.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionException("O valor de transação é inválido.");
        }

        BalanceEntity balanceEntity = balancePersistence.findById(transaction.getBalanceId());

        if (balanceEntity == null)
            throw new TransactionException("Balanço não encontrado.");

        if (balanceEntity.isLocked())
            throw new TransactionException("Balanço com restrições.");


        BigDecimal transactionValue = transaction.getValue();
        BigDecimal amount;

        if (transaction.isDebitOperation()) {
            amount = balanceEntity.getAmount().subtract(transactionValue);

            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new TransactionException("Saldo insuficiente.");
            }

        } else {
            amount = balanceEntity.getAmount().add(transactionValue);
        }

        balanceEntity.setAmount(amount);

        TransactionEntity transactionEntity = new TransactionEntity(transaction, balanceEntity);

        transactionPersistence.save(transactionEntity);
    }

    @Override
    public void list(String balanceId, TransactionType transactionType) {
        transactionPersistence.findByBalanceIdAndType(balanceId, transactionType);
    }


}
