package io.github.henriquecesar.wallet.extract.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.henriquecesar.wallet.transaction.dto.TransactionOutput;
import io.github.henriquecesar.wallet.core.constants.ApplicationConstants;
import io.github.henriquecesar.wallet.domain.Account;
import io.github.henriquecesar.wallet.domain.Balance;
import io.github.henriquecesar.wallet.domain.Extract;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExtractOutput {

    private String accountId;
    private BigDecimal totalBalance;
    private ExtractTransactionList items;

    public ExtractOutput(Extract extract) {
        if (extract == null) return;

        this.accountId = Optional.ofNullable(extract.getBalance())
                .map(Balance::getAccount)
                .map(Account::getId)
                .orElse(null);

        this.totalBalance = Optional.ofNullable(extract.getBalance())
                .map(Balance::getTotal)
                .map(b -> b.setScale(ApplicationConstants.SCALE_MONEY, RoundingMode.DOWN))
                .orElse(null);

        this.items = new ExtractTransactionList(extract.getTransactions() == null ? null : extract.getTransactions().map(TransactionOutput::new));
    }

}
