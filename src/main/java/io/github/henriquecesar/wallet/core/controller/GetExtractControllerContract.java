package io.github.henriquecesar.wallet.core.controller;

import io.github.henriquecesar.wallet.extract.dto.ExtractOutput;
import io.github.henriquecesar.wallet.core.UserInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

public interface GetExtractControllerContract {

    @ApiOperation("Get extract by account and balance IDs")
    @GetMapping("/accounts/{accountId}/balances/{balanceId}/extract")
    ResponseEntity<ExtractOutput> getAmount(
            @ApiParam(value = "Account ID", required = true) @PathVariable String accountId,
            @ApiParam(value = "Balance ID", required = true) @PathVariable String balanceId,
            @ApiParam(value = "Page number (optional)") @RequestParam int value,
            @ApiParam(value = "Transaction type (optional)", allowableValues = "RECARGA, TRANSFERENCIA, COMPRA, ESTORNO, CANCELAMENTO") @RequestParam(required = false) String type,
            @ApiIgnore UserInfo userInfo);
}

