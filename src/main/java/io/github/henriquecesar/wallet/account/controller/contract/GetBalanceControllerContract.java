package io.github.henriquecesar.wallet.account.controller.contract;

import io.github.henriquecesar.wallet.account.dto.BalanceOutput;
import io.github.henriquecesar.wallet.core.UserInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface GetBalanceControllerContract {

    @ApiOperation("Get balance amount by account and balance IDs")
    @GetMapping("/accounts/{accountId}/balances/{balanceId}")
    ResponseEntity<BalanceOutput> getAmount(
            @ApiParam(value = "Account ID", required = true) @PathVariable String accountId,
            @ApiParam(value = "Balance ID", required = true) @PathVariable String balanceId,
            @ApiParam(hidden = true) UserInfo userInfo);

}

