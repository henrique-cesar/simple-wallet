package io.github.henriquecesar.wallet.balance.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.henriquecesar.wallet.domain.Account;
import io.github.henriquecesar.wallet.domain.Balance;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BalanceOutput {
    private String balanceId;
    private String userId;
    private BigDecimal amount;

    public BalanceOutput(Balance balance) {
        if (balance == null) return;

        this.balanceId = balance.getId();
        this.userId = Optional.ofNullable(balance.getAccount()).map(Account::getId).orElse(null);
        this.amount = balance.getTotal();
    }
}
