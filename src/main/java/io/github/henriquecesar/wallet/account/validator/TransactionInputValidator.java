package io.github.henriquecesar.wallet.account.validator;

import br.com.fluentvalidator.AbstractValidator;
import io.github.henriquecesar.wallet.account.dto.TransactionInput;
import io.github.henriquecesar.wallet.core.constants.ApplicationConstants;
import io.github.henriquecesar.wallet.domain.TransactionType;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Predicate;

import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThan;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.*;
import static io.github.henriquecesar.wallet.core.constants.ApplicationConstants.SCALE_MONEY;
import static java.util.function.Predicate.not;

@Component
public class TransactionInputValidator extends AbstractValidator<TransactionInput> {

    @Override
    public void rules() {
        ruleFor(TransactionInput::getBalanceId)
            .must(not(stringEmptyOrNull()))
            .withMessage("O campo balance_id é obrigatório.")
            .withFieldName("balance_id");

        ruleFor(TransactionInput::getValue)
            .must(not(nullValue()))
            .withMessage("O campo value é obrigatório.")
            .withFieldName("value")
            .critical()

            .must(stringMatches(ApplicationConstants.REGEX_CURRENCY))
            .when(not(nullValue()))
            .withMessage("O campo value não contém um valor válido.")
            .withFieldName("value")
            .critical()

            .must(greaterThan(BigDecimal.ZERO.setScale(SCALE_MONEY, RoundingMode.DOWN).toString()))
            .withMessage("O campo value precisa ser maior que 0.")
            .withFieldName("value");

        ruleFor(TransactionInput::getOperation)
            .must(not(stringEmptyOrNull()))
            .withMessage("O campo operation é obrigatório.")
            .withFieldName("operation")

            .must(inAllowedTransactions())
            .when(not(nullValue()))
            .withMessage("O campo operation não contém um tipo de transação válido.")
            .withFieldName("operation");

    }

    private Predicate<String> inAllowedTransactions() {
        return transactionInput -> TransactionType.entryOf(transactionInput) != null;
    }

}
