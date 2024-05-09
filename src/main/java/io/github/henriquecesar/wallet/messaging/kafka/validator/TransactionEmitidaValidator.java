package io.github.henriquecesar.wallet.messaging.kafka.validator;

import br.com.fluentvalidator.AbstractValidator;
import io.github.henriquecesar.wallet.core.constants.ApplicationConstants;
import io.github.henriquecesar.wallet.transaction_emitida.TransactionEmitida;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThan;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static br.com.fluentvalidator.predicate.StringPredicate.stringMatches;
import static io.github.henriquecesar.wallet.core.constants.ApplicationConstants.SCALE_MONEY;
import static java.util.function.Predicate.not;

@Component
public class TransactionEmitidaValidator extends AbstractValidator<TransactionEmitida> {

    @Override
    public void rules() {
        ruleFor(transactionEmitida -> transactionEmitida.getValor().toString())
                .must(not(stringEmptyOrNull()))
                .withMessage("O campo numero_identificador_transacao não pode ser nulo.")
                .withFieldName("numero_identificador_transacao")

                .must(stringMatches(ApplicationConstants.REGEX_CURRENCY))
                .when(not(nullValue()))
                .withMessage("O campo value não contém um valor válido.")
                .withFieldName("value")
                .critical()

                .must(greaterThan(BigDecimal.ZERO.setScale(SCALE_MONEY, RoundingMode.DOWN).toString()))
                .withMessage("O campo value precisa ser maior que 0.")
                .withFieldName("value");
    }
}
