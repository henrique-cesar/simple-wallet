package io.github.henriquecesar.wallet.core.exception;

import br.com.fluentvalidator.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler
    ResponseEntity<Object> handleException(final ValidationException ex) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getValidationResult());
    }

    @ExceptionHandler
    ResponseEntity<Object> handleException(final TransactionException ex) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("error", ex.getMessage()));
    }

}
