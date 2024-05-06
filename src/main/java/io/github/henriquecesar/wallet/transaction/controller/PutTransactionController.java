package io.github.henriquecesar.wallet.transaction.controller;

import io.github.henriquecesar.wallet.account.dto.TransactionInput;
import io.github.henriquecesar.wallet.account.validator.TransactionInputValidator;
import io.github.henriquecesar.wallet.domain.CanalOrigem;
import io.github.henriquecesar.wallet.core.UserInfo;
import io.github.henriquecesar.wallet.core.command.CommandContext;
import io.github.henriquecesar.wallet.core.command.CommandProcessorTransactionHandler;
import io.github.henriquecesar.wallet.core.exception.BadRequestException;
import io.github.henriquecesar.wallet.core.service.AuthorizationService;
import io.github.henriquecesar.wallet.service.auth.aspect.NeedsAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.github.henriquecesar.wallet.core.constants.ApplicationConstants.ACCOUNT_ID;
import static io.github.henriquecesar.wallet.core.constants.ApplicationConstants.CANAL_ORIGEM;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PutTransactionController {
    private final TransactionInputValidator inputValidator;
    private final AuthorizationService authorizationService;
    private final CommandProcessorTransactionHandler<?> commandHandler;

    @NeedsAuthorization
    @PutMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<?> realize(@PathVariable String accountId, @RequestBody TransactionInput data, UserInfo userInfo) {

        authorizationService.validate(userInfo, accountId);
        // Usuário validado com sucesso.

        inputValidator.validate(data).isInvalidThrow(BadRequestException.class);
        // Entrada validada com sucesso.

        CommandContext context = new CommandContext(data.toModel());
        context.put(ACCOUNT_ID, accountId);
        context.put(CANAL_ORIGEM, CanalOrigem.API);

        commandHandler.process(CanalOrigem.API, context);

        return ResponseEntity.ok().build();
    }

}
