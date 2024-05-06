package io.github.henriquecesar.wallet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Balance {
    private String id;
    private Account account;
    private BigDecimal total;
    private boolean locked;
    private List<Transaction> transactions;

    public Balance(String id, Account account, BigDecimal total) {
        this.id = id;
        this.account = account;
        this.total = total;
    }

}
