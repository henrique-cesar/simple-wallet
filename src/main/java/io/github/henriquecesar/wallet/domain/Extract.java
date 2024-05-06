package io.github.henriquecesar.wallet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Extract {

    private Balance balance;

    private List<Transaction> transactions;

}
