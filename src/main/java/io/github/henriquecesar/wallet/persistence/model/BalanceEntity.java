package io.github.henriquecesar.wallet.persistence.model;

import io.github.henriquecesar.wallet.core.constants.ApplicationConstants;
import io.github.henriquecesar.wallet.domain.Balance;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
@Entity(name = "balance")
@Table(name = "balance", schema = "simple_wallet")
public class BalanceEntity {

    @Id
    private String id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private AccountEntity account;

    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

    @NotNull
    private boolean locked;

    @OneToMany(mappedBy = "balance", cascade = CascadeType.MERGE)
    private List<TransactionEntity> transactions;

    public Balance fromModel() {
        Balance balance = new Balance();
        balance.setId(this.id);
        balance.setAccount(this.account.fromModel());
        balance.setTotal(this.amount.setScale(ApplicationConstants.SCALE_MONEY, RoundingMode.DOWN));
        balance.setLocked(this.locked);

        return balance;
    }

}
