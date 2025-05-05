package co.com.nequi.techlead.challenge.api.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SiteHandler {

    public Mono<ServerResponse> createSite(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("createSite");
    }

    public Mono<ServerResponse> updateSite(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("updateSite");
    }
}
