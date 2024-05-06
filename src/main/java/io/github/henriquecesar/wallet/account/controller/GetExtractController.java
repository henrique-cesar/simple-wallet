package io.github.henriquecesar.wallet.account.controller;

import io.github.henriquecesar.wallet.account.controller.contract.GetExtractControllerContract;
import io.github.henriquecesar.wallet.account.dto.ExtractOutput;
import io.github.henriquecesar.wallet.core.UserInfo;
import io.github.henriquecesar.wallet.core.exception.UnauthorizedException;
import io.github.henriquecesar.wallet.core.service.AuthorizationService;
import io.github.henriquecesar.wallet.core.service.AccountService;
import io.github.henriquecesar.wallet.domain.Extract;
import io.github.henriquecesar.wallet.domain.TransactionType;
import io.github.henriquecesar.wallet.service.auth.aspect.NeedsAuthorization;
import io.github.henriquecesar.wallet.service.auth.client.AuthorizationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetExtractController implements GetExtractControllerContract {

    private final AuthorizationService authorizationService;
    private final AccountService accountService;

    @NeedsAuthorization
    @GetMapping("/accounts/{accountId}/balances/{balanceId}/extract")
    public ResponseEntity<ExtractOutput> getAmount(@PathVariable String accountId, @PathVariable String balanceId, @RequestParam(required = false) String type, UserInfo userInfo) {

        authorizationService.validate(userInfo, accountId);

        Extract extract = type == null ?
                accountService.getExtract(accountId, balanceId) :
                accountService.getExtract(accountId, balanceId, TransactionType.entryOf(type));

        ExtractOutput data = new ExtractOutput(extract);

        return ResponseEntity.ok(data);

    }

}
