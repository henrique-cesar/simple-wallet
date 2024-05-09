package io.github.henriquecesar.wallet.transaction.adapter;

import io.github.henriquecesar.wallet.core.exception.BusinessException;
import io.github.henriquecesar.wallet.core.persistence.BalancePersistence;
import io.github.henriquecesar.wallet.core.persistence.TransactionPersistence;
import io.github.henriquecesar.wallet.core.service.TransactionService;
import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.persistence.model.BalanceEntity;
import io.github.henriquecesar.wallet.persistence.model.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final BalancePersistence balancePersistence;
    private final TransactionPersistence transactionPersistence;

    @Override
    public void preAuthorize(Transaction transaction) {
        BalanceEntity balance = balancePersistence.findById(transaction.getBalanceId());

        if (balance.isLocked()) {
            throw new BusinessException("Balanço possui restrições.");
        }

        if (transaction.isDebitOperation()) {
            BigDecimal amountBalance = balance.getAmount();
            BigDecimal newBalance = amountBalance.subtract(transaction.getValue());

            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Saldo insuficiente.");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void realize(Transaction transaction) {
        if (transaction == null) return;

        if (!transaction.isCreditOperation() && !transaction.isDebitOperation())
            throw new BusinessException("Operação não permitida.");

        if (transaction.getValue() == null || transaction.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("O valor de transação é inválido.");
        }

        if (transactionPersistence.findById(transaction.getId()) != null) {
            throw new BusinessException("Essa transação já existe.");
        }

        BalanceEntity balanceEntity = balancePersistence.findById(transaction.getBalanceId());

        if (balanceEntity == null)
            throw new BusinessException("Balanço não encontrado.");

        if (balanceEntity.isLocked())
            throw new BusinessException("Balanço com restrições.");


        BigDecimal transactionValue = transaction.getValue();
        BigDecimal amount;

        if (transaction.isDebitOperation()) {
            amount = balanceEntity.getAmount().subtract(transactionValue);

            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Saldo insuficiente.");
            }

        } else {
            amount = balanceEntity.getAmount().add(transactionValue);
        }

        balanceEntity.setAmount(amount);

        TransactionEntity transactionEntity = new TransactionEntity(transaction, balanceEntity);

        transactionPersistence.save(transactionEntity);
    }

}
