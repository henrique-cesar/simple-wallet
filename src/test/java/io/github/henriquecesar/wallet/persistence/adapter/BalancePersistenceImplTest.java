package io.github.henriquecesar.wallet.persistence.adapter;

import io.github.henriquecesar.wallet.persistence.model.BalanceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class BalancePersistenceImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<BalanceEntity> query;

    @InjectMocks
    private BalancePersistenceImpl balancePersistence;

    @Test
    public void testFindById() {
        String balanceId = "balance-id";
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setId(balanceId);

        Mockito.when(entityManager.find(BalanceEntity.class, balanceId)).thenReturn(balanceEntity);

        BalanceEntity result = balancePersistence.findById(balanceId);

        assertEquals(balanceId, result.getId());
    }

    @Test
    public void testFindByIdAndAccountId() {
        String balanceId = "balance-id";
        String accountId = "account-id";
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setId(balanceId);

        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(BalanceEntity.class))).thenReturn(query);
        Mockito.when(query.setParameter("id", balanceId)).thenReturn(query);
        Mockito.when(query.setParameter("accountId", accountId)).thenReturn(query);
        Mockito.when(query.getSingleResult()).thenReturn(balanceEntity);

        BalanceEntity result = balancePersistence.findByIdAndAccountId(balanceId, accountId);

        assertEquals(balanceId, result.getId());
    }

    @Test
    public void testExistsByIdAndAccountId() {
        String balanceId = "balance-id";
        String accountId = "account-id";

        TypedQuery typedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(typedQuery.getSingleResult()).thenReturn(1L);
        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Long.class))).thenReturn(typedQuery);

        boolean result = balancePersistence.existsByIdAndAccountId(balanceId, accountId);

        assertTrue(result);
    }

    @Test
    public void testNotExistsByIdAndAccountId() {
        String balanceId = "balance-id";
        String accountId = "account-id";

        TypedQuery typedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(typedQuery.getSingleResult()).thenReturn(0L);
        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(Long.class))).thenReturn(typedQuery);

        boolean result = balancePersistence.existsByIdAndAccountId(balanceId, accountId);

        assertFalse(result);
    }

}