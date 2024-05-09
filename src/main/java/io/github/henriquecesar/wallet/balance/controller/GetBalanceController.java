package io.github.henriquecesar.wallet.balance.controller;

import io.github.henriquecesar.wallet.balance.dto.BalanceOutput;
import io.github.henriquecesar.wallet.core.controller.GetBalanceControllerContract;
import io.github.henriquecesar.wallet.core.UserInfo;
import io.github.henriquecesar.wallet.core.service.AuthorizationService;
import io.github.henriquecesar.wallet.core.service.BalanceService;
import io.github.henriquecesar.wallet.domain.Balance;
import io.github.henriquecesar.wallet.service.auth.aspect.NeedsAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetBalanceController implements GetBalanceControllerContract {

    private final AuthorizationService authorizationService;
    private final BalanceService balanceService;

    @NeedsAuthorization
    @GetMapping("/accounts/{accountId}/balances/{balanceId}")
    public ResponseEntity<BalanceOutput> getAmount(@PathVariable String accountId, @PathVariable String balanceId, UserInfo userInfo) {

        authorizationService.validate(userInfo, accountId);

        Balance balance = balanceService.getBy(accountId, balanceId);

        BalanceOutput balanceOutput = new BalanceOutput(balance);

        return ResponseEntity.ok(balanceOutput);
    }

}
