package io.github.henriquecesar.wallet.extract.controller;

import io.github.henriquecesar.wallet.core.controller.GetExtractControllerContract;
import io.github.henriquecesar.wallet.core.service.ExtractService;
import io.github.henriquecesar.wallet.extract.dto.ExtractOutput;
import io.github.henriquecesar.wallet.core.UserInfo;
import io.github.henriquecesar.wallet.core.service.AuthorizationService;
import io.github.henriquecesar.wallet.domain.Extract;
import io.github.henriquecesar.wallet.domain.TransactionType;
import io.github.henriquecesar.wallet.service.auth.aspect.NeedsAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetExtractController implements GetExtractControllerContract {

    private final AuthorizationService authorizationService;
    private final ExtractService extractService;

    @NeedsAuthorization
    @GetMapping("/accounts/{accountId}/balances/{balanceId}/extract")
    public ResponseEntity<ExtractOutput> getAmount(@PathVariable String accountId,
                                                   @PathVariable String balanceId,
                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                   @RequestParam(required = false) String type, UserInfo userInfo) {

        authorizationService.validate(userInfo, accountId);

        Extract extract = type == null ?
                extractService.getBy(accountId, balanceId, page) :
                extractService.getBy(accountId, balanceId, TransactionType.entryOf(type), page);

        ExtractOutput data = new ExtractOutput(extract);

        return ResponseEntity.ok(data);

    }

}
