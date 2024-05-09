package io.github.henriquecesar.wallet.balance.adapter;

import io.github.henriquecesar.wallet.core.exception.BusinessException;
import io.github.henriquecesar.wallet.core.persistence.BalancePersistence;
import io.github.henriquecesar.wallet.core.persistence.TransactionPersistence;
import io.github.henriquecesar.wallet.domain.Balance;
import io.github.henriquecesar.wallet.persistence.model.BalanceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BalanceServiceImplTest {

    @Mock
    private BalancePersistence balancePersistence;

    @Mock
    private TransactionPersistence transactionPersistence;

    @InjectMocks
    private BalanceServiceImpl balanceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetByExistingBalanceId() {
        String balanceId = UUID.randomUUID().toString();
        String accountId = UUID.randomUUID().toString();
        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.setId(balanceId);

        Mockito.when(balancePersistence.findByIdAndAccountId(balanceId, accountId)).thenReturn(balanceEntity);

        Balance balance = balanceService.getBy(accountId, balanceId);

        Mockito.verify(balancePersistence).findByIdAndAccountId(balanceId, accountId);

        assertNotNull(balance);
        assertEquals(balanceId, balance.getId());
    }

    @Test
    public void testGetByNonExistingBalanceId() {
        Mockito.when(balancePersistence.findById(Mockito.anyString())).thenReturn(null);

        assertThrows(BusinessException.class, () -> {
            balanceService.getBy("account-id", "balance-id");
        });
    }

}