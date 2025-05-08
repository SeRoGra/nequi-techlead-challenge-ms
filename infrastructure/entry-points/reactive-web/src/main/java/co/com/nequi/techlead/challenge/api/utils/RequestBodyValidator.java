package co.com.nequi.techlead.challenge.api.utils;

import co.com.nequi.techlead.challenge.exceptions.BadRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

public class RequestBodyValidator {

    public static <T> Mono<T> validateBody(Mono<T> bodyMono, Validator validator) {
        return bodyMono.flatMap(body -> {
            Set<ConstraintViolation<T>> violations = validator.validate(body);
            if (!violations.isEmpty()) {
                String errors = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                return Mono.error(new BadRequestException(errors));
            }
            return Mono.just(body);
        });
    }

}
