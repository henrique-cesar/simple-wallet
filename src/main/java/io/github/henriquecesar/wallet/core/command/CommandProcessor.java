package io.github.henriquecesar.wallet.core.command;

public interface CommandProcessor<U> {
    U process(final CommandContext context);
}
