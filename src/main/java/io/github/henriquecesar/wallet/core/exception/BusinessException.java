package io.github.henriquecesar.wallet.core.exception;

public class BusinessException extends RuntimeException {

    private final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
