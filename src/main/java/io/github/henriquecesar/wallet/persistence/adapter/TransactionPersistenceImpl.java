package io.github.henriquecesar.wallet.persistence.adapter;

import io.github.henriquecesar.wallet.core.persistence.TransactionPersistence;
import io.github.henriquecesar.wallet.domain.TransactionType;
import io.github.henriquecesar.wallet.persistence.model.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class TransactionPersistenceImpl implements TransactionPersistence {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TransactionEntity save(TransactionEntity transaction) {
        entityManager.merge(transaction);
        return transaction;
    }

    @Override
    public TransactionEntity findById(String id) {
        return entityManager.find(TransactionEntity.class, id);
    }

    @Override
    public Page<TransactionEntity> findByBalanceId(String balanceId, Pageable pageable) {
        List<TransactionEntity> transactions = entityManager.createQuery(
                        "SELECT t FROM transaction t WHERE t.balance.id = :balanceId ORDER BY t.createdAt DESC", TransactionEntity.class)
                .setParameter("balanceId", balanceId)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(transactions, pageable, countTotalTransactionsByBalanceId(balanceId));
    }

    @Override
    public Page<TransactionEntity> findByBalanceIdAndType(String balanceId, TransactionType transactionType, Pageable pageable) {
        List<TransactionEntity> transactions = entityManager.createQuery(
                "SELECT t FROM transaction t WHERE t.balance.id = :balanceId AND t.transactionType = :transactionType ORDER BY t.createdAt DESC", TransactionEntity.class)
                .setParameter("balanceId", balanceId)
                .setParameter("transactionType", transactionType)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return new PageImpl<>(transactions, pageable, countTotalTransactionsByBalanceId(balanceId, transactionType));
    }

    private long countTotalTransactionsByBalanceId(String balanceId) {
        return entityManager.createQuery(
                "SELECT COUNT(t) FROM transaction t WHERE t.balance.id = :balanceId", Long.class)
                .setParameter("balanceId", balanceId)
                .getSingleResult();
    }

    private long countTotalTransactionsByBalanceId(String balanceId, TransactionType transactionType) {
        return entityManager.createQuery(
                        "SELECT COUNT(t) FROM transaction t WHERE t.balance.id = :balanceId AND t.transactionType = :transactionType", Long.class)
                .setParameter("balanceId", balanceId)
                .setParameter("transactionType", transactionType)
                .getSingleResult();
    }

}
