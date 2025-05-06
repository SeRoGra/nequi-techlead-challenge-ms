package co.com.nequi.techlead.challenge.api.config;

import co.com.nequi.techlead.challenge.api.dto.exception.ErrorResponse;
import co.com.nequi.techlead.challenge.exceptions.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Order(-2)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        return Mono.error(throwable)
                .onErrorResume(NotFoundException.class, ex -> this.exception(exchange, ex))
                .onErrorResume(ex -> this.exception(exchange, ex)).then();
    }

    Mono<Void> exception(ServerWebExchange exchange, NotFoundException ex) {
        return buildResponse(exchange, HttpStatus.NOT_FOUND, ex);
    }

    Mono<Void> exception(ServerWebExchange exchange, Throwable ex) {
        return buildResponse(exchange, HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private byte[] convertToJson(ErrorResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(response);
        } catch (JsonProcessingException e) {
            return new byte[0];
        }
    }

    private Mono<Void> buildResponse(ServerWebExchange exchange, HttpStatus status, Throwable ex) {
        ErrorResponse error = ErrorResponse.builder()
                .code(status.name())
                .message(ex.getMessage())
                .path(exchange.getRequest().getPath().value())
                .build();

        byte[] json = convertToJson(error);
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(json)));
    }
}
