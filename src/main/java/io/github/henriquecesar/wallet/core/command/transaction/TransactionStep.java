package io.github.henriquecesar.wallet.core.command.transaction;

import io.github.henriquecesar.wallet.domain.CanalOrigem;

import java.util.Set;

import static io.github.henriquecesar.wallet.domain.CanalOrigem.*;

public enum TransactionStep {

    PRE_AUTHORIZATION(1, Set.of(API, SQS, KAFKA)),
    EXECUTE(2, Set.of(API, SQS, KAFKA)),
    SEND_OK_SQS(3, Set.of(SQS)),
    SEND_OK_KAFKA(4, Set.of(KAFKA));

    private final int priority;
    private final Set<CanalOrigem> acceptedOrigins;

    TransactionStep(int priority, Set<CanalOrigem> acceptedOrigins) {
        this.priority = priority;
        this.acceptedOrigins = acceptedOrigins;
    }

    public int getPriority() {
        return priority;
    }

    public Set<CanalOrigem> getAcceptedOrigins() {
        return acceptedOrigins;
    }
}
