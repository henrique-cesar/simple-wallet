package io.github.henriquecesar.wallet.service.fraude;

import io.github.henriquecesar.wallet.core.service.FraudeService;
import io.github.henriquecesar.wallet.domain.Transaction;
import org.springframework.stereotype.Service;

@Service
public class FraudeServiceImpl implements FraudeService {

    @Override
    public void verify(Transaction transaction) {
        return;
    }

}
