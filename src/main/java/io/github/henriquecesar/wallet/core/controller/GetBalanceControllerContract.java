package io.github.henriquecesar.wallet.core.controller;

import io.github.henriquecesar.wallet.balance.dto.BalanceOutput;
import io.github.henriquecesar.wallet.core.UserInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.annotations.ApiIgnore;

public interface GetBalanceControllerContract {

    @ApiOperation("Get balance amount by account and balance IDs")
    @GetMapping("/accounts/{accountId}/balances/{balanceId}")
    ResponseEntity<BalanceOutput> getAmount(
            @ApiParam(value = "Account ID", required = true) @PathVariable String accountId,
            @ApiParam(value = "Balance ID", required = true) @PathVariable String balanceId,
            @ApiIgnore UserInfo userInfo);

}

