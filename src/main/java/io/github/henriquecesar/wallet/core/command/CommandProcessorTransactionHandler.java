package io.github.henriquecesar.wallet.core.command;

import io.github.henriquecesar.wallet.domain.CanalOrigem;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
public class CommandProcessorTransactionHandler<T> {

    private final List<CommandProcessorTransactionStep> processorSteps;

    public CommandProcessorTransactionHandler(List<CommandProcessorTransactionStep> processorSteps) {
        this.processorSteps = processorSteps;
    }

    public void process(final CanalOrigem canalOrigem, final CommandContext context) {
        processorSteps.stream()
            .filter(filterByOrigin(canalOrigem))
            .sorted(Comparator.comparingInt(current -> current.getStep().getPriority()))
            .forEach(handler -> handler.process(context));
    }

    private Predicate<CommandProcessorTransactionStep> filterByOrigin(CanalOrigem canalOrigem) {
        return processor -> processor.getStep().getAcceptedOrigins().contains(canalOrigem);
    }

}
