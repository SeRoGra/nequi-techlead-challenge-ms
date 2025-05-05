package co.com.nequi.techlead.challenge.api.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {
    public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("createProduct");
    }

    public Mono<ServerResponse> updateProductName(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("updateProductName");
    }

    public Mono<ServerResponse> updateProductStock(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("updateProductStock");
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("deleteProduct");
    }

    public Mono<ServerResponse> getTopProductsBySite(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("getTopProductsBySite");
    }
}
