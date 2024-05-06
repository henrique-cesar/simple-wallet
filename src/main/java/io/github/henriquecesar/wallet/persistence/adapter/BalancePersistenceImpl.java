package io.github.henriquecesar.wallet.persistence.adapter;

import io.github.henriquecesar.wallet.core.persistence.BalancePersistence;
import io.github.henriquecesar.wallet.persistence.model.BalanceEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
@Transactional
public class BalancePersistenceImpl implements BalancePersistence {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BalanceEntity findById(String id) {
        return entityManager.find(BalanceEntity.class, id);
    }

    @Override
    public BalanceEntity findByIdAndAccountId(String id, String accountId) {
        String jpql = "SELECT b FROM BalanceEntity b WHERE b.id = :id AND b.accountId = :accountId";
        TypedQuery<BalanceEntity> query = entityManager.createQuery(jpql, BalanceEntity.class);
        query.setParameter("id", id);
        query.setParameter("accountId", accountId);
        return query.getSingleResult();
    }

    @Override
    public boolean existsByIdAndAccountId(String balanceId, String accountId) {
        String jpql = "SELECT COUNT(b) FROM balance b WHERE b.id = :balanceId AND b.account.id = :accountId";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("balanceId", balanceId);
        query.setParameter("accountId", accountId);
        Long count = query.getSingleResult();
        return count > 0;
    }

}
