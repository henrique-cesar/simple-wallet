package io.github.henriquecesar.wallet.account.controller;

import io.github.henriquecesar.wallet.account.controller.contract.GetBalanceControllerContract;
import io.github.henriquecesar.wallet.account.controller.contract.GetExtractControllerContract;
import io.github.henriquecesar.wallet.account.dto.BalanceOutput;
import io.github.henriquecesar.wallet.account.dto.ExtractOutput;
import io.github.henriquecesar.wallet.core.UserInfo;
import io.github.henriquecesar.wallet.core.exception.UnauthorizedException;
import io.github.henriquecesar.wallet.core.service.AuthorizationService;
import io.github.henriquecesar.wallet.core.service.AccountService;
import io.github.henriquecesar.wallet.domain.Balance;
import io.github.henriquecesar.wallet.service.auth.aspect.NeedsAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AccountService balanceService;

    @NeedsAuthorization
    @GetMapping("/accounts/{accountId}/balances/{balanceId}")
    public ResponseEntity<BalanceOutput> getAmount(@PathVariable String accountId, @PathVariable String balanceId, UserInfo userInfo) {

        authorizationService.validate(userInfo, accountId);

        Balance balance = balanceService.getBalanceAmount(accountId, balanceId);

        BalanceOutput balanceOutput = new BalanceOutput(balance);

        return ResponseEntity.ok(balanceOutput);
    }

}
