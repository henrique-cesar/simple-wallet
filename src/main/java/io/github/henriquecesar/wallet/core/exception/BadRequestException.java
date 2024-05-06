package io.github.henriquecesar.wallet.core.exception;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.fluentvalidator.exception.ValidationException;

public class BadRequestException extends ValidationException {

    public BadRequestException(ValidationResult validationResult) {
        super(validationResult);
    }

}
