package io.github.henriquecesar.wallet.persistence.model;

import io.github.henriquecesar.wallet.domain.Account;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
@Table(name = "account", schema = "simple_wallet")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank
    private String username;

    @OneToMany(mappedBy = "account", cascade = CascadeType.MERGE)
    private Set<BalanceEntity> balances;

    @NotBlank
    private String email;

    public Account fromModel() {
        Account account = new Account();
        account.setId(this.id);
        account.setEmail(this.email);
        account.setUserName(this.username);

        return account;
    }
}
