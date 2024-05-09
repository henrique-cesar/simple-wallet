package io.github.henriquecesar.wallet.balance.adapter;

import io.github.henriquecesar.wallet.core.exception.BusinessException;
import io.github.henriquecesar.wallet.core.persistence.BalancePersistence;
import io.github.henriquecesar.wallet.core.service.BalanceService;
import io.github.henriquecesar.wallet.domain.Balance;
import io.github.henriquecesar.wallet.persistence.model.BalanceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalancePersistence balancePersistence;

    @Override
    public Balance getBy(String accountId, String balanceId) {
        BalanceEntity balanceDB = balancePersistence.findByIdAndAccountId(balanceId, accountId);

        if (balanceDB == null) {
            throw new BusinessException("Balanço não encontrado.");
        }

        return balanceDB.fromModel();
    }

}
