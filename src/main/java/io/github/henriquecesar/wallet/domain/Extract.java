package io.github.henriquecesar.wallet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
public class Extract {

    private Balance balance;

    private Page<Transaction> transactions;

}
